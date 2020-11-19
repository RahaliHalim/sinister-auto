package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.BusinessEntity;
import com.gaconnecte.auxilium.domain.Functionality;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.UserAccess;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.BusinessEntityRepository;
import com.gaconnecte.auxilium.service.mapper.BusinessEntityMapper;
import com.gaconnecte.auxilium.repository.search.UserExtraSearchRepository;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.UserAccessWorkDTO;
import com.gaconnecte.auxilium.service.dto.EntityProfileAccessDTO;
import com.gaconnecte.auxilium.service.dto.PermissionAccessDTO;
import com.gaconnecte.auxilium.service.dto.UserProfileDTO;
import com.gaconnecte.auxilium.service.dto.UserAccessDTO;
import com.gaconnecte.auxilium.service.dto.ProfileAccessDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserPartnerModeDTO;
import com.gaconnecte.auxilium.service.mapper.FunctionalityMapper;
import com.gaconnecte.auxilium.service.mapper.UserPartnerModeMapper;
import com.gaconnecte.auxilium.repository.FunctionalityRepository;
import com.gaconnecte.auxilium.repository.UserAccessRepository;

import com.gaconnecte.auxilium.repository.UserProfileRepository;
import com.gaconnecte.auxilium.service.mapper.UserProfileMapper;
import com.gaconnecte.auxilium.service.mapper.UserExtraMapper;
import java.util.HashMap;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserExtra.
 */
@Service
@Transactional
public class UserExtraService {

    private final Logger log = LoggerFactory.getLogger(UserExtraService.class);

    private final UserExtraRepository userExtraRepository;

    private final UserExtraMapper userExtraMapper;

    private final BusinessEntityRepository businessEntityRepository;

    private final FunctionalityRepository functionalityRepository;

    private final BusinessEntityMapper businessEntityMapper;

    private final FunctionalityMapper functionalityMapper;

    private final UserExtraSearchRepository userExtraSearchRepository;

    private final UserProfileRepository userProfileRepository;

    private final ProfileAccessService profileAccessService;

    private final UserAccessRepository userAccessRepository;

    private final UserProfileMapper userProfileMapper;

    private final UserPartnerModeMapper userPartnerModeMapper;

    public UserExtraService(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper,
            FunctionalityRepository functionalityRepository, BusinessEntityRepository businessEntityRepository,
            UserExtraRepository userExtraRepository, UserExtraMapper userExtraMapper,
            BusinessEntityMapper businessEntityMapper, FunctionalityMapper functionalityMapper,
            UserExtraSearchRepository userExtraSearchRepository, UserPartnerModeMapper userPartnerModeMapper,
            ProfileAccessService profileAccessService, UserAccessRepository userAccessRepository) {
        this.userExtraRepository = userExtraRepository;
        this.userExtraMapper = userExtraMapper;
        this.businessEntityMapper = businessEntityMapper;
        this.functionalityMapper = functionalityMapper;
        this.businessEntityRepository = businessEntityRepository;
        this.functionalityRepository = functionalityRepository;
        this.userExtraSearchRepository = userExtraSearchRepository;
        this.profileAccessService = profileAccessService;
        this.userProfileMapper = userProfileMapper;
        this.userProfileRepository = userProfileRepository;
        this.userPartnerModeMapper = userPartnerModeMapper;
        this.userAccessRepository = userAccessRepository;

    }

    /**
     * Save a userExtra.
     *
     * @param userExtraDTO the entity to save
     * @return the persisted entity
     */
    public UserExtraDTO save(UserExtraDTO userExtraDTO) {
        log.debug("Request to save UserExtra : {}", userExtraDTO);
        UserExtra userExtra = userExtraMapper.toEntity(userExtraDTO);
        userExtra = userExtraRepository.save(userExtra);
        UserExtraDTO result = userExtraMapper.toDto(userExtra);
        userExtraSearchRepository.save(userExtra);
        return result;
    }

    /**
     * Get all the userExtras.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserExtraDTO> findAll() {
        log.debug("Request to get all UserExtras");
        return userExtraRepository.findAll().stream().map(userExtraMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userExtra by user id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserExtraDTO finPersonneIdByUser(Long id) {
        log.debug("Request to get UserExtra : {}", id);
        UserExtra userExtra = userExtraRepository.findByUser(id);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        return userExtraDTO;
    }

    @Transactional(readOnly = true)
    public UserExtraDTO findByPersonProfil(Long personId, Long profilId) {
        log.debug("Request to get UserExtra by person and profil : {}", personId);
        UserExtra userExtra = userExtraRepository.findByPersonProfil(personId, profilId);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);
        return userExtraDTO;
    }

    @Transactional(readOnly = true)
    public Set<UserExtraDTO> findUserByPersonProfil(Long personId, Long profilId) {
        log.debug("Request to get UserExtra by person and profil : {}", personId);
        Set<UserExtra> usersExtra = userExtraRepository.findUserByPersonProfil(personId, profilId);
        if (CollectionUtils.isNotEmpty(usersExtra)) {
            return usersExtra.stream().map(userExtraMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * Get one userExtra by user id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserExtraDTO findOneByUser(Long id) {
        log.debug("Request to get UserExtra : {}", id);
        UserExtra userExtra = userExtraRepository.findByUser(id);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);

        UserAccessWorkDTO userAccessWorkDTO = new UserAccessWorkDTO();
        Set<UserProfileDTO> profiles = new HashSet<>();

        Map<String, UserAccessDTO> userAccesssMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(userExtraDTO.getAccesses())) { // get user access
            for (UserAccessDTO ua : userExtraDTO.getAccesses()) {
                userAccesssMap.put(ua.getBusinessEntityId().longValue() + "#" + ua.getFunctionalityId().longValue(),
                        ua);
                profiles.add(userProfileMapper.toDto(userProfileRepository.findOne(ua.getProfileId())));
            }
        }
        if (CollectionUtils.isNotEmpty(profiles)) { // get user access
            for (UserProfileDTO up : profiles) {
                List<EntityProfileAccessDTO> entityProfileAccesss = new LinkedList<>();
                Map<Long, EntityProfileAccessDTO> map = new HashMap<>();
                if (CollectionUtils.isNotEmpty(userAccessWorkDTO.getEntityProfileAccesss())) {
                    for (EntityProfileAccessDTO entityProfileAccess : userAccessWorkDTO.getEntityProfileAccesss()) {
                        map.put(entityProfileAccess.getId(), entityProfileAccess);
                    }
                }
                List<ProfileAccessDTO> profileAccesss = profileAccessService.findAllByProfile(up.getId());
                if (CollectionUtils.isNotEmpty(profileAccesss)) {
                    for (ProfileAccessDTO profileAccess : profileAccesss) {
                        UserAccessDTO userAccessDTO = userAccesssMap.get(profileAccess.getBusinessEntityId().longValue()
                                + "#" + profileAccess.getFunctionalityId().longValue());
                        if (userAccessDTO != null) {
                            profileAccess.setId(userAccessDTO.getId());
                            profileAccess.setActive(Boolean.TRUE);
                        } else {
                            profileAccess.setActive(Boolean.FALSE);
                        }
                        if (map.containsKey(profileAccess.getBusinessEntityId())) {
                            map.get(profileAccess.getBusinessEntityId()).addProfileAccesss(profileAccess);
                        } else {
                            EntityProfileAccessDTO epa = new EntityProfileAccessDTO();
                            epa.setId(profileAccess.getBusinessEntityId());
                            epa.setLabel(profileAccess.getBusinessEntityLabel());
                            epa.addProfileAccesss(profileAccess);
                            map.put(profileAccess.getBusinessEntityId(), epa);
                        }
                    }
                    entityProfileAccesss.addAll(map.values());
                    userAccessWorkDTO.setEntityProfileAccesss(entityProfileAccesss);
                }
            }
        }
        List<UserProfileDTO> up = new LinkedList<>();
        up.addAll(profiles);
        userAccessWorkDTO.setProfiles(up);

        userAccessWorkDTO.setAddFlag(Boolean.TRUE);
        userExtraDTO.setUserAccessWork(userAccessWorkDTO);
        return userExtraDTO;
    }

    /**
     * Get one userExtra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserExtraDTO findOne(Long id) {
        log.debug("Request to get UserExtra : {}", id);
        UserExtra userExtra = userExtraRepository.findOne(id);
        return userExtraMapper.toDto(userExtra);
    }

    /**
     * Is the user has functionality
     * 
     * @param entityId
     * @param userId
     * @param functionalityId
     * @return
     */
    @Transactional(readOnly = true)
    public Boolean isUserHasFunctionality(Long entityId, Long userId, Long functionalityId) {
        log.debug("Request to get UserExtra : {}", userId);
        List<UserAccess> accesss = userAccessRepository.findAllByUserExtraAndBusinessEntityAndFunctionality(
                new UserExtra(userId), new BusinessEntity(entityId), new Functionality(functionalityId));
        return CollectionUtils.isNotEmpty(accesss) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Transactional(readOnly = true)
    public Set<UserExtraDTO> findByProfil(Long idProfil) {
        log.debug("Request to get UserExtra By profil : {}", idProfil);

        Set<UserExtra> usersExtra = userExtraRepository.findByProfil(idProfil);
        if (CollectionUtils.isNotEmpty(usersExtra)) {
            return usersExtra.stream().map(userExtraMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Transactional(readOnly = true)
    public Set<UserExtraDTO> findAllResponsibles() {
        log.debug("Request to get Responsible UserExtra ");

        Set<UserExtra> usersExtra = userExtraRepository.findAllResponsibles();
        if (CollectionUtils.isNotEmpty(usersExtra)) {
            return usersExtra.stream().map(userExtraMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**/
    @Transactional(readOnly = true)
    public Set<UserExtraDTO> findByProfils() {
        log.debug("Request to get UserExtra By profil : {}");

        Set<UserExtra> usersExtra = userExtraRepository.findByProfils();
        if (CollectionUtils.isNotEmpty(usersExtra)) {
            return usersExtra.stream().map(userExtraMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Transactional(readOnly = true)
    public PermissionAccessDTO permission(Long userId, Long id) {
        PermissionAccessDTO permissionAccessDTO = new PermissionAccessDTO();
        UserExtra userExtra = userExtraRepository.findByUser(userId);
        if (CollectionUtils.isNotEmpty(userExtra.getAccesses())) {
            for (UserAccess ua : userExtra.getAccesses()) {
                if (ua.getBusinessEntity() != null) {
                    if (ua.getBusinessEntity().getId() != null) {
                        if (ua.getBusinessEntity().getId().equals(id)) {
                            if (ua.getBusinessEntity() != null) {
                                if (ua.getFunctionality().getId() != null) {
                                    switch (ua.getFunctionality().getId().intValue()) {
                                    case 1:
                                        permissionAccessDTO.setCanLister(true);
                                        permissionAccessDTO.setCanConsult(true);
                                        break;
                                    case 2:
                                        permissionAccessDTO.setCanCreate(true);
                                        break;
                                    case 4:
                                        permissionAccessDTO.setCanConsult(true);
                                        break;
                                    case 5:
                                        permissionAccessDTO.setCanDelete(true);
                                        break;
                                    case 3:
                                        permissionAccessDTO.setCanUpdate(true);
                                        break;
                                    case 6:
                                        permissionAccessDTO.setCanActive(true);
                                        permissionAccessDTO.setCanDesactive(true);
                                        break;
                                    case 8:
                                        permissionAccessDTO.setCanGenerate(true);
                                        break;
                                    case 89:
                                        permissionAccessDTO.setCanConsultDemandPec(true);
                                        break;
                                    case 90:
                                        permissionAccessDTO.setCanTrait(true);
                                        break;
                                    case 80:
                                        permissionAccessDTO.setCanModifStatus(true);
                                        break;
                                    case 83:
                                        permissionAccessDTO.setCanModifReserveLifted(true);
                                        break;
                                    case 85:
                                        permissionAccessDTO.setCanAprouveSinPec(true);
                                        break;
                                    case 77:
                                        permissionAccessDTO.setCanReopenPecRefus(true);
                                        break;
                                    case 78:
                                        permissionAccessDTO.setCanReopenPecCanceled(true);
                                        break;
                                    case 36:
                                        permissionAccessDTO.setCanCancelSinPec(true);
                                        break;
                                    case 43:
                                        permissionAccessDTO.setCanConfirmCancelSinPec(true);
                                        break;
                                    case 37:
                                        permissionAccessDTO.setCanRefusSinPec(true);
                                        break;
                                    case 44:
                                        permissionAccessDTO.setCanConfirmRefusSinPec(true);
                                        break;
                                    case 71:
                                        permissionAccessDTO.setCanGenereBonSortie(true);
                                        break;
                                    case 45:
                                        permissionAccessDTO.setCanVerifOriginalsPrinted(true);
                                        break;
                                    case 10:
                                        permissionAccessDTO.setCanSignatureBonSortie(true);
                                        break;
                                    case 107:
                                        permissionAccessDTO.setCanAssignRepairer(true);

                                        break;
                                    case 109:
                                        permissionAccessDTO.setCanCancellationAssignmentRepair(true);
                                        break;
                                    case 14:
                                        permissionAccessDTO.setCanMissionAnExpert(true);
                                        break;
                                    case 28:
                                        permissionAccessDTO.setCanMissionExpertCancellation(true);
                                        break;
                                    case 93:
                                        permissionAccessDTO.setCanEditVehicleReception(true);
                                        break;
                                    case 95:
                                        permissionAccessDTO.setCanEditQuoteQuote(true);
                                        break;

                                    case 97:
                                        permissionAccessDTO.setCanEditConfirmationQuote(true);

                                        break;
                                    case 99:
                                        permissionAccessDTO.setCanUpdateQuotation(true);
                                        break;
                                    case 101:
                                        permissionAccessDTO.setCanValidationQueryrectify(true);
                                        break;
                                    case 29:
                                        permissionAccessDTO.setCanExpertOpinion(true);
                                        break;
                                    case 34:
                                        permissionAccessDTO.setCanSignatureAgreement(true);
                                        break;
                                    case 41:
                                        permissionAccessDTO.setCanConfirmationOfTheSupplementaryQuote(true);
                                        break;
                                    case 35:
                                        permissionAccessDTO.setCanBilling(true);
                                        break;
                                    case 87:
                                        permissionAccessDTO.setCanDecide(true);
                                        break;
                                    case 111:
                                        permissionAccessDTO.setCanCreate(true);
                                        break;
                                    case 112:
                                        permissionAccessDTO.setCanUpdate(true);
                                        break;
                                    case 116:
                                        permissionAccessDTO.setCanRepriseSinPec(true);
                                        break;
                                    case 114:
                                        permissionAccessDTO.setCanDismantling(true);
                                        break;
                                    case 118:
                                        permissionAccessDTO.setCanConfirmModifPrix(true);
                                        break;
                                    case 125:
                                        permissionAccessDTO.setCanModifPrix(true);
                                        break;
                                    case 127:
                                        permissionAccessDTO.setCanLister(true);
                                        permissionAccessDTO.setCanConsult(true);
                                        break;
                                    case 128:
                                        permissionAccessDTO.setCanCreate(true);
                                        break;
                                    case 129:
                                        permissionAccessDTO.setCanUpdate(true);
                                        break;
                                    case 130:
                                        permissionAccessDTO.setCanLister(true);
                                        permissionAccessDTO.setCanConsult(true);
                                        break;
                                    case 131:
                                        permissionAccessDTO.setCanCreate(true);
                                        break;
                                    case 132:
                                        permissionAccessDTO.setCanUpdate(true);
                                        break;
                                    case 133:
                                        permissionAccessDTO.setCanDelete(true);
                                        break;
                                    case 134:
                                        permissionAccessDTO.setCanDelete(true);
                                        break;
                                    case 30:
                                        permissionAccessDTO.setCanApprouvApec(true);
                                        break;
                                    case 31:
                                        permissionAccessDTO.setCanModifApec(true);
                                        break;
                                    case 32:
                                        permissionAccessDTO.setCanValidApec(true);
                                        break;
                                    case 33:
                                        permissionAccessDTO.setCanValidAssurApec(true);
                                        break;
                                    case 9:
                                        permissionAccessDTO.setCanImprimApec(true);
                                        break;
                                    case 146:
                                        permissionAccessDTO.setCanAjoutDemandPecExpertise(true);
                                        break;
                                    case 148:
                                        permissionAccessDTO.setCanModifWithDerogation(true);
                                        break;

                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        return permissionAccessDTO;
    }

    @Transactional(readOnly = true)
    public Set<UserPartnerModeDTO> findPartnerModeByUser(Long idUser) {
        log.debug("Request to get Partner and Mode By user : {}", idUser);

        Set<UserPartnerMode> usersPartnerMode = userExtraRepository.findPartnerModeByUser(idUser);
        if (CollectionUtils.isNotEmpty(usersPartnerMode)) {
            return usersPartnerMode.stream().map(userPartnerModeMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * Delete the userExtra by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExtra : {}", id);
        userExtraRepository.delete(id);
        userExtraSearchRepository.delete(id);
    }

    /**
     * Search for the userExtra corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserExtraDTO> search(String query) {
        log.debug("Request to search UserExtras for query {}", query);
        return StreamSupport.stream(userExtraSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .map(userExtraMapper::toDto).collect(Collectors.toList());
    }

}
