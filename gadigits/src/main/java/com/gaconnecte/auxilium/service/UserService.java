package com.gaconnecte.auxilium.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.config.Constants;
import com.gaconnecte.auxilium.domain.Authority;
import com.gaconnecte.auxilium.domain.BusinessEntity;
import com.gaconnecte.auxilium.domain.ElementMenu;
import com.gaconnecte.auxilium.domain.Functionality;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.UserAccess;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserProfile;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.RefModeGestion;
import com.gaconnecte.auxilium.domain.Agency;
import com.gaconnecte.auxilium.repository.AuthorityRepository;
import com.gaconnecte.auxilium.repository.PersistentTokenRepository;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.UserProfileRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.search.UserSearchRepository;
import com.gaconnecte.auxilium.security.AuthoritiesConstants;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.dto.EntityProfileAccessDTO;
import com.gaconnecte.auxilium.service.dto.ProfileAccessDTO;
import com.gaconnecte.auxilium.service.dto.UserDTO;
import com.gaconnecte.auxilium.service.dto.UserPartnerModeDTO;
import com.gaconnecte.auxilium.service.mapper.UserMapper;
import com.gaconnecte.auxilium.service.util.RandomUtil;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import java.util.Collections;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserExtraRepository userExtraRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserSearchRepository userSearchRepository;

    private final PersistentTokenRepository persistentTokenRepository;

    private final AuthorityRepository authorityRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            UserSearchRepository userSearchRepository, PersistentTokenRepository persistentTokenRepository,
            AuthorityRepository authorityRepository, UserMapper userMapper, UserExtraRepository userExtraRepository,
            UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSearchRepository = userSearchRepository;
        this.persistentTokenRepository = persistentTokenRepository;
        this.authorityRepository = authorityRepository;
        this.userMapper = userMapper;
        this.userExtraRepository = userExtraRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key).map(user -> {
            // activate given user for the registration key.
            user.setActivated(true);
            user.setActivationKey(null);
            userSearchRepository.save(user);
            log.debug("Activated user: {}", user);
            return user;
        });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400))).map(user -> {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetKey(null);
            user.setResetDate(null);
            return user;
        });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail).filter(User::getActivated).map(user -> {
            user.setResetKey(RandomUtil.generateResetKey());
            user.setResetDate(Instant.now());
            return user;
        });
    }

    public User createUser(String login, String password, String firstName, String lastName, String email,
            String imageUrl, String langKey) {
        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        // newUser.setLogin(login);
        newUser.setLogin(email);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setImageUrl(imageUrl);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        userSearchRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public void updateGroup(Long id) {

        userRepository.updateGroup(id);
    }

    public User createUser(UserDTO userDTO) {

        User user = new User();
        // user.setLogin(userDTO.getLogin());

        user.setLogin(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        user.setIdExterne(userDTO.getIdExterne());
        user.setIdProfil(userDTO.getIdProfil());
        user.setIdGroup(userDTO.getIdGroup());
        if (userDTO.getLangKey() == null) {
            user.setLangKey("fr"); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }

        userDTO.setAuthorities(new HashSet<>(Collections.singletonList(AuthoritiesConstants.USER)));

        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            userDTO.getAuthorities().forEach(authority -> authorities.add(authorityRepository.findOne(authority)));
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);

        user = userRepository.save(user);
        userSearchRepository.save(user);
        log.debug("Created Information for User: {}", user);

        // Create and save the UserExtra entity
        UserExtra newUserExtra = new UserExtra();
        newUserExtra.setId(user.getId());
        newUserExtra.setUser(user);
        newUserExtra.setProfile(userDTO.getUserExtra().getProfileId() != null ? userProfileRepository.findOne(userDTO.getUserExtra().getProfileId()) : null);
        if (CollectionUtils.isNotEmpty(userDTO.getUserExtra().getUserAccessWork().getEntityProfileAccesss())) {
            for (EntityProfileAccessDTO epa : userDTO.getUserExtra().getUserAccessWork().getEntityProfileAccesss()) {
                for (ProfileAccessDTO profileAccessDto : epa.getProfileAccesss()) {
                    if (profileAccessDto.getActive()) {
                        UserAccess ua = new UserAccess();
                        ua.setLabel(profileAccessDto.getLabel());
                        ua.setCode(profileAccessDto.getCode());
                        ua.setTranslationCode(profileAccessDto.getTranslationCode());
                        ua.setUrl(profileAccessDto.getUrl());
                        ua.setLeaf(Boolean.TRUE);
                        ua.setBusinessEntity(new BusinessEntity(profileAccessDto.getBusinessEntityId()));
                        ua.setFunctionality(new Functionality(profileAccessDto.getFunctionalityId()));
                        ua.setElementMenu(profileAccessDto.getElementMenuId() != null ? new ElementMenu(profileAccessDto.getElementMenuId()) : null);
                        ua.setProfile(new UserProfile(profileAccessDto.getProfileId()));
                        newUserExtra.addAccess(ua);
                    }
                }
            }
        }

				// Added By Ridha
				if(CollectionUtils.isNotEmpty(userDTO.getUserExtra().getUserPartnerModes())) {
                   for (UserPartnerModeDTO pmDTO : userDTO.getUserExtra().getUserPartnerModes()) {
					   UserPartnerMode userPartnerMode = new UserPartnerMode();
					   userPartnerMode.setPartner(new Partner(pmDTO.getPartnerId()));
					   if(pmDTO.getModeId() != null){
					   userPartnerMode.setModeGestion(new RefModeGestion(pmDTO.getModeId()));
					   }
					   if(pmDTO.getCourtierId() != null){
					   userPartnerMode.setAgency(new Agency(pmDTO.getCourtierId()));
					   }
					   userPartnerMode.setUser(user);
					   newUserExtra.addUserPartnerMode(userPartnerMode);
				   }
				}
				if(userDTO.getUserExtra().getUserBossId() != null){
                  newUserExtra.setUserBossId(userDTO.getUserExtra().getUserBossId());
				}
				if(userDTO.getUserExtra().getPersonId() != null){
                  newUserExtra.setPersonId(userDTO.getUserExtra().getPersonId());
				}
                userExtraRepository.save(newUserExtra);
                log.debug("Created Information for UserExtra: {}", newUserExtra);
		return user;
	}


	public User updateUser(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
   		user.setLogin(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setImageUrl(userDTO.getImageUrl());
		user.setIdExterne(userDTO.getIdExterne());
		user.setIdProfil(userDTO.getIdProfil());
		user.setIdGroup(userDTO.getIdGroup());
		if (userDTO.getLangKey() == null) {
			user.setLangKey("fr"); // default language
		} else {
			user.setLangKey(userDTO.getLangKey());
		}

		userDTO.setAuthorities(new HashSet<>(Collections.singletonList(AuthoritiesConstants.USER)));

		if (userDTO.getAuthorities() != null) {
			Set<Authority> authorities = new HashSet<>();
			userDTO.getAuthorities().forEach(authority -> authorities.add(authorityRepository.findOne(authority)));
			user.setAuthorities(authorities);
		}
		String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
		user.setPassword(encryptedPassword);
		user.setResetKey(RandomUtil.generateResetKey());
		user.setResetDate(Instant.now());
		//user.setActivated(true);
		user.setActivated(userDTO.getActivated());

		user = userRepository.save(user);
		userSearchRepository.save(user);
		log.debug("Created Information for User: {}", user);


                // Create and save the UserExtra entity
                UserExtra newUserExtra = new UserExtra();
                newUserExtra.setId(user.getId());
                newUserExtra.setUser(user);
                newUserExtra.setProfile(userDTO.getUserExtra().getProfileId() != null ? userProfileRepository.findOne(userDTO.getUserExtra().getProfileId()) : null);
                if(CollectionUtils.isNotEmpty(userDTO.getUserExtra().getUserAccessWork().getEntityProfileAccesss())) {
                    for (EntityProfileAccessDTO epa : userDTO.getUserExtra().getUserAccessWork().getEntityProfileAccesss()) {
                        for (ProfileAccessDTO profileAccessDto : epa.getProfileAccesss()) {
                            if(profileAccessDto.getActive()) {
                                UserAccess ua = new UserAccess();
                                ua.setLabel(profileAccessDto.getLabel());
                                ua.setCode(profileAccessDto.getCode());
                                ua.setTranslationCode(profileAccessDto.getTranslationCode());
                                ua.setUrl(profileAccessDto.getUrl());
                                ua.setLeaf(Boolean.TRUE);
                                ua.setBusinessEntity(new BusinessEntity(profileAccessDto.getBusinessEntityId()));
                                ua.setFunctionality(new Functionality(profileAccessDto.getFunctionalityId()));
                                ua.setElementMenu(profileAccessDto.getElementMenuId() != null ? new ElementMenu(profileAccessDto.getElementMenuId()) : null);
                                ua.setProfile(new UserProfile(profileAccessDto.getProfileId()));
                                newUserExtra.addAccess(ua);
                            }
                        }

            }
        }
        // Added By Ridha
				if(CollectionUtils.isNotEmpty(userDTO.getUserExtra().getUserPartnerModes())) {
                   for (UserPartnerModeDTO pmDTO : userDTO.getUserExtra().getUserPartnerModes()) {
                UserPartnerMode userPartnerMode = new UserPartnerMode();
                userPartnerMode.setPartner(new Partner(pmDTO.getPartnerId()));
					   if(pmDTO.getModeId() != null){
                    userPartnerMode.setModeGestion(new RefModeGestion(pmDTO.getModeId()));
                }
					   if(pmDTO.getCourtierId() != null){
					   userPartnerMode.setAgency(new Agency(pmDTO.getCourtierId()));
					   }
                userPartnerMode.setUser(user);
                newUserExtra.addUserPartnerMode(userPartnerMode);
            }
        }
				if(userDTO.getUserExtra().getUserBossId() != null){
            newUserExtra.setUserBossId(userDTO.getUserExtra().getUserBossId());
        }
				if(userDTO.getUserExtra().getPersonId() != null){
            newUserExtra.setPersonId(userDTO.getUserExtra().getPersonId());
        }
        userExtraRepository.save(newUserExtra);
                log.debug("Created Information for UserExtra: {}", newUserExtra);
        return user;
    }



    public UserDTO createUserReturnDto(UserDTO userDTO) {
        User user = new User();
        // user.setLogin(userDTO.getLogin());
        user.setLogin(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        user.setIdExterne(userDTO.getIdExterne());
        user.setIdProfil(userDTO.getIdProfil());
        user.setIdGroup(userDTO.getIdGroup());
        if (userDTO.getLangKey() == null) {
            user.setLangKey("fr"); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            userDTO.getAuthorities().forEach(authority -> authorities.add(authorityRepository.findOne(authority)));
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);

        userRepository.save(user);
        userSearchRepository.save(user);
        log.debug("Created Information for User: {}", user);
        UserDTO dto = new UserDTO(user);
        return dto;
    }

    /**
     * Update basic information (first name, last name, email, language) for the
     * current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        userRepository.findOneByLoginIgnoreCase(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLangKey(langKey);
            user.setImageUrl(imageUrl);
            userSearchRepository.save(user);
            log.debug("Changed Information for User: {}", user);
        });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUserDto(UserDTO userDTO) {
        log.debug("Request to update User : {}", userDTO);
        User user = userMapper.userDTOToUser(userDTO);
        user = userRepository.save(user);
        Optional<UserDTO> result = Optional.of(userMapper.userToUserDTO(user));
        return result;
    }

    public void deleteUser(String login) {
        userRepository.findOneByLoginIgnoreCase(login).ifPresent(user -> {
            userRepository.delete(user);
            userSearchRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    // delete using id as a parameter
    public void deleteUser(Long id) {
        userRepository.findOneById(id).ifPresent(user -> {
            userRepository.delete(user);
            userSearchRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public User changePassword(String password, String oldpassword) {

    	Optional<User> user = userRepository.findOneByLoginIgnoreCase(SecurityUtils.getCurrentUserLogin());
        if(user.isPresent()) {
        	
        	if(passwordEncoder.matches(oldpassword, user.get().getPassword())) {

    		log.debug("Changed password for User: {}", user);
            String encryptedPassword = passwordEncoder.encode(password);
            User userToUpdate = user.get();
        	userToUpdate.setPassword(encryptedPassword);
            return userRepository.save(userToUpdate);
            }
        	
        	
        	
        };
        
        return null;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersWithoutPagination() {

        return userRepository.findUsersWithoutPagination();

    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        return userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).orElse(null);
    }

    /**
     * Persistent Token are used for providing automatic authentication, they
     * should be automatically deleted after 30 days.
     * <p>
     * This is scheduled to get fired everyday, at midnight.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOldPersistentTokens() {
        LocalDate now = LocalDate.now();
        persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).forEach(token -> {
            log.debug("Deleting token {}", token.getSeries());
            User user = token.getUser();
            user.getPersistentTokens().remove(token);
            persistentTokenRepository.delete(token);
        });
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        List<User> users = userRepository
                .findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
            userSearchRepository.delete(user);
        }
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    /*
	 * return User by login
     */
    public User findOneUserByLogin(String login) {

        return userRepository.findOneUserByLogin(login);
    }

    public User update(Long idexterne, Long idUser) {

        log.debug("before get user {}", idexterne);
        User user = userRepository.getOne(idUser);
        log.debug("after get user {}", idUser);
        user.setIdExterne(idexterne);

        User updateuser = userRepository.saveAndFlush(user);
        log.debug("after update user {}", idexterne);

        return updateuser;

    }

}
