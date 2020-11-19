package com.gaconnecte.auxilium.service;

import java.time.Instant;
import java.util.*;

import com.gaconnecte.auxilium.domain.*;
import com.gaconnecte.auxilium.repository.CelluleRepository;
import com.gaconnecte.auxilium.repository.UserCelluleRepository;
import org.activiti.engine.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gaconnecte.auxilium.repository.PersonnePhysiqueRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.search.UserSearchRepository;
import com.gaconnecte.auxilium.service.dto.PersonnePhysiqueDTO;
import com.gaconnecte.auxilium.service.util.RandomUtil;

@Service
public class CustomUserService {

    private final Logger log = LoggerFactory.getLogger(CustomUserService.class);

    @Autowired
    public IdentityService identityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSearchRepository userSearchRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    @Autowired
    private UserCelluleRepository userCelluleRepository;

    @Autowired
    private CelluleRepository celluleRepository;

    // create
    public org.activiti.engine.identity.User createAcitivitiUser(User newUser) {
        org.activiti.engine.identity.User user = identityService.newUser(newUser.getLogin());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.isPictureSet();

        List<UserCellule> userCellules = userCelluleRepository.findByUser(newUser.getId());
        List<Long> cellules = new ArrayList<>();
        for (UserCellule uc : userCellules) {
            cellules.add(uc.getCellule().getId());
        }
        List<Cellule> lc = celluleRepository.findAll(cellules);
        identityService.saveUser(user);
        for (Authority authority : newUser.getAuthorities()) {
            if (authority.isIsInterne() == true) {
                for (Cellule cc : lc) {
                	System.out.println("authoritiinterne"+user.getId()+","+ cc.getName() + "_" + authority.getName());
                    identityService.createMembership(user.getId(), cc.getName() + "_" + authority.getName());
                }
            }
            identityService.createMembership(user.getId(), authority.getName());
        }
        return user;
    }
    
    public org.activiti.engine.identity.User createActUser(Long userId) {
        User newUser = userRepository.findOneById(userId).get();
        org.activiti.engine.identity.User user = identityService.newUser(newUser.getLogin());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.isPictureSet();

        identityService.saveUser(user);
        for (Authority authority : newUser.getAuthorities()) {
            identityService.createMembership(user.getId(), authority.getName());
        }
        return user;
    }

    public PersonnePhysique createPersonnePhysiqueFromUser(User user) {
        PersonnePhysique personnePhysique = new PersonnePhysique();
        personnePhysique.setUser(user);
        personnePhysique.setNom(user.getFirstName());
        personnePhysique.prenom(user.getLastName());
        personnePhysique.setPremMail(user.getEmail());
        personnePhysique.setIsUtilisateur(Boolean.TRUE);
        personnePhysiqueRepository.save(personnePhysique);
        return personnePhysique;
    }

    public User createUserFromPersonePysique(String firstName, String lastName, String email, String role) {
        User user = new User();
        user.setLogin(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setLangKey("fr"); // default language
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName("ROLE_USER");
        authorities.add(authority);
        user.setAuthorities(authorities);
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        userRepository.save(user);
        userSearchRepository.save(user);
        log.debug("Created Information for User: {}", user);
        createAcitivitiUser(user);
        return user;

    }

    // delete using id
    public void deleteUser(Long id) {
        User user = userRepository.findOneById(id).get();
        if (user != null) {
            user = userService.getUserWithAuthorities(user.getId());
            deleteActivitiUser(user);
        }
    }

    // update
    public void updateUser(Long idUser) {
        User user = userService.getUserWithAuthorities(idUser);
        updateAcitivitiUser(user);
        //updatePersonnePhysiqueFromUser(user);
    }

    public void updateUser(Long idUser, String oldEmail) {
        User user = userService.getUserWithAuthorities(idUser);
        
        updateAcitivitiUser(user);
        //updatePersonnePhysiqueFromUser(user);
    }

     // update
    public User updateGroupUser(Long idUser, Long idGroup) {
       User user = userRepository.findOneById(idUser).get();
       user.setIdGroup(idGroup);
       return userRepository.save(user);
    }

    public org.activiti.engine.identity.User updateAcitivitiUser(User user) {
        deleteActivitiUser(user);
        return createAcitivitiUser(user);
    }

    public PersonnePhysique updatePersonnePhysiqueFromUser(User user) {
        PersonnePhysique personne = personnePhysiqueRepository.findOneByPremMail(user.getEmail()).get();
        if (personne != null) {
            personne.setNom(user.getFirstName());
            personne.setPrenom(user.getLastName());
            personnePhysiqueRepository.save(personne);
        }
        return personne;
    }

    public User updateUserFromPersonePysique(Long idUser, String firstName, String lastName) {
        User user = userService.getUserWithAuthorities(idUser);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userRepository.save(user);
            userSearchRepository.save(user);
            log.debug("Created Information for User: {}", user);
            updateAcitivitiUser(user);
        }
        return user;
    }

    // delete
    public void deleteUserFromUser(String login) {
        User user = userRepository.findOneByLoginIgnoreCase(login).get();
        if (user != null) {
            user = userService.getUserWithAuthorities(user.getId());
            deleteActivitiUser(user);
            deletePersonnePhysique(user);
        }
    }
    // delete using id
    public void deleteUserFromUser(Long id) {
        User user = userRepository.findOneById(id).get();
        if (user != null) {
            user = userService.getUserWithAuthorities(user.getId());
            deleteActivitiUser(user);
            deletePersonnePhysique(user);
        }
    }
    

    public void deleteUserFromPersonnePhysique(PersonnePhysiqueDTO personne) {
        if (personne != null) {
            User user = userRepository.findOneByLoginIgnoreCase(personne.getPremMail()).get();
            user = userService.getUserWithAuthorities(user.getId());
            userService.deleteUser(personne.getPremMail());
            deleteActivitiUser(user);
        }
    }

    public void deleteActivitiUser(User user) {
        for (Authority authority : user.getAuthorities()) {
        	System.out.println("delete authoriti membership"+authority);
            identityService.deleteMembership(user.getLogin(), authority.getName());
        }
        identityService.deleteUser(user.getLogin());
    }

    public void deletePersonnePhysique(User user) {
        Optional<PersonnePhysique> personne = personnePhysiqueRepository.findOneByPremMail(user.getEmail());
        if (personne.isPresent()) {
            personnePhysiqueRepository.delete(personne.get().getId());
        }
    }

    public void addRoleToUser(Long idPersonnePhysique, String role) {
        PersonnePhysique personne = personnePhysiqueRepository.findOne(idPersonnePhysique);
        if (personne != null) {
            User user = userRepository.findOneByLoginIgnoreCase(personne.getPremMail()).get();
            user = userService.getUserWithAuthorities(user.getId());
            Authority authority = new Authority();
            authority.setName(role);
            user.getAuthorities().add(authority);
            userRepository.save(user);
            userSearchRepository.save(user);
            updateAcitivitiUser(user);
        }
    }

}
