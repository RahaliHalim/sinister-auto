package com.gaconnecte.auxilium.service.impl;

import com.gaconnecte.auxilium.service.ApecService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.domain.Apec;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.domain.ViewSinisterPec;
import com.gaconnecte.auxilium.repository.ApecRepository;
import com.gaconnecte.auxilium.repository.SinisterPecRepository;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.search.ApecSearchRepository;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.mapper.ApecMapper;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.repository.UserRepository;
import org.apache.commons.collections.CollectionUtils;

import com.gaconnecte.auxilium.service.mapper.SinisterPecMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.collections.CollectionUtils;

/**
 * Service Implementation for managing Apec.
 */
@Service
@Transactional

public class ApecServiceImpl implements ApecService {

    private final Logger log = LoggerFactory.getLogger(ApecServiceImpl.class);

    private final ApecRepository apecRepository;

    private final ApecMapper apecMapper;

    private final SinisterPecMapper sinisterPecMapper;

    private final SinisterPecRepository sinisterPecRepository;

    private final ApecSearchRepository apecSearchRepository;

    private final UserExtraRepository userExtraRepository;

    private final UserRepository userRepository;
    @Autowired
    private UserExtraService userExtraService;

    public ApecServiceImpl(ApecRepository apecRepository, ApecMapper apecMapper,
            ApecSearchRepository apecSearchRepository, SinisterPecRepository sinisterPecRepository,
            SinisterPecMapper sinisterPecMapper, UserExtraRepository userExtraRepository,
            UserRepository userRepository) {
        this.apecRepository = apecRepository;
        this.apecMapper = apecMapper;
        this.apecSearchRepository = apecSearchRepository;
        this.sinisterPecRepository = sinisterPecRepository;
        this.sinisterPecMapper = sinisterPecMapper;
        this.userExtraRepository = userExtraRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a apec.
     *
     * @param apecDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApecDTO save(ApecDTO apecDTO) {
        log.debug("Request to save Apec : {}", apecDTO);
        Apec apec = apecMapper.toEntity(apecDTO);
        apec = apecRepository.save(apec);
        ApecDTO result = apecMapper.toDto(apec);
        return result;
    }

    /**
     * Get all the apecs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApecDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Apecs");
        return apecRepository.findAll(pageable).map(apecMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApecDTO> findAllAccordByDecision(Integer etat) {
        log.debug("Request to get all Accord By Decision");
        return apecMapper.toDto(apecRepository.findAllAccordByDecision(etat));

    }

    @Override
    @Transactional(readOnly = true)
    public List<ApecDTO> findAllAccordByStatus(Integer etat) {
        log.debug("Request to get all Accord By Status ");
        UserExtra userExtraAssign = null;
        UserExtra responsablePec = null;
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userRepository.findOneUserByLogin(login);
        UserExtra userExtra = userExtraRepository.findByUser(user.getId());
        Integer size = userExtra.getUserPartnerModes().size();
        List<Apec> list = apecRepository.findAllAccordByStatus(etat);
        List<Apec> listFiltredByProfil = new ArrayList<Apec>();

        if (etat == 6) {
            if (!userExtra.getProfile().getId().equals(24L) && !userExtra.getProfile().getId().equals(25L)
                    && !userExtra.getProfile().getId().equals(23L) && !userExtra.getProfile().getId().equals(26L)
                    && !userExtra.getProfile().getId().equals(4L) && !userExtra.getProfile().getId().equals(5L)
                    && !userExtra.getProfile().getId().equals(6L) && !userExtra.getProfile().getId().equals(7L)) {
                listFiltredByProfil = list;
            } else {
                for (Apec apec : list) {

                    if (apec.getSinisterPec().getAssignedTo() != null) {
                        userExtraAssign = userExtraRepository.findByUser(apec.getSinisterPec().getAssignedTo().getId());
                        responsablePec = userExtraRepository.findByUser(userExtraAssign.getUserBossId());
                    }
                    if (apec.getSinisterPec() != null) {
                        if (apec.getSinisterPec().getMode() != null) {
                            if (userExtra.getProfile() != null) {
                                if (apec.getSinisterPec().getMode().getId().equals(7L)
                                        && userExtra.getId().equals(userExtraAssign.getId())) {
                                    listFiltredByProfil.add(apec);
                                } else if (!apec.getSinisterPec().getMode().getId().equals(7L) && apec.getTtc() < 3000D
                                        && userExtra.getId().equals(userExtraAssign.getId())) {
                                    listFiltredByProfil.add(apec);
                                } else if (!apec.getSinisterPec().getMode().getId().equals(7L) && 3000D <= apec.getTtc()
                                        && apec.getTtc() <= 15000D
                                        && userExtra.getId().equals(userExtraAssign.getUserBossId())) {
                                    listFiltredByProfil.add(apec);
                                } else if (!apec.getSinisterPec().getMode().getId().equals(7L) && apec.getTtc() > 15000D
                                        && userExtra.getId().equals(responsablePec.getUserBossId())) {
                                    listFiltredByProfil.add(apec);
                                }
                            }
                        }
                    }
                }
            }
            return apecMapper.toDto(listFiltredByProfil);
        } else if (etat == 7) {
            if (!userExtra.getProfile().getId().equals(24L) && !userExtra.getProfile().getId().equals(25L)
                    && !userExtra.getProfile().getId().equals(23L) && !userExtra.getProfile().getId().equals(26L)
                    && !userExtra.getProfile().getId().equals(4L) && !userExtra.getProfile().getId().equals(5L)
                    && !userExtra.getProfile().getId().equals(6L) && !userExtra.getProfile().getId().equals(7L)) {
                listFiltredByProfil = list;
            } else if (userExtra.getProfile().getId().equals(24L)) {
                for (Apec apec : list) {

                    if (userExtra.getPersonId()
                            .equals(apec.getSinisterPec().getSinister().getContract().getAgence().getId())) {
                        listFiltredByProfil.add(apec);
                    }
                }
            } else if (userExtra.getProfile().getId().equals(25L)) {
                for (Apec apec : list) {

                    if (userExtra.getPersonId()
                            .equals(apec.getSinisterPec().getSinister().getContract().getClient().getId())) {
                        listFiltredByProfil.add(apec);
                    }
                }
            } else {
                for (Apec apec : list) {
                    if (apec.getSinisterPec().getAssignedTo() != null) {
                        userExtraAssign = userExtraRepository.findByUser(apec.getSinisterPec().getAssignedTo().getId());
                    }
                    if (userExtraAssign.getId().equals(userExtra.getId())) {
                        listFiltredByProfil.add(apec);
                    }
                }
            }
            return apecMapper.toDto(listFiltredByProfil);
        } else if (etat != 6 && etat != 7) {
            if (size.equals(0)) {
                if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
                    for (Apec apec : list) {
                        if (userExtra.getPersonId()
                                .equals(apec.getSinisterPec().getSinister().getContract().getClient().getId())) {
                            listFiltredByProfil.add(apec);
                        }
                    }
                } else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(24L)) {
                    for (Apec apec : list) {
                        if (userExtra.getPersonId()
                                .equals(apec.getSinisterPec().getSinister().getContract().getAgence().getId())) {
                            listFiltredByProfil.add(apec);
                        }
                    }
                } else {
                    listFiltredByProfil = list;
                }
            } else {
                if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
                    for (Apec apec : list) {
                        if (userExtra.getPersonId()
                                .equals(apec.getSinisterPec().getSinister().getContract().getClient().getId())) {
                            listFiltredByProfil.add(apec);
                        }
                    }
                } else if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
                    for (UserPartnerMode userPartnerMode : userExtra.getUserPartnerModes()) {
                        for (Apec apec : list) {
                            if (userPartnerMode.getModeGestion().getId().equals(apec.getSinisterPec().getMode().getId())
                                    && userPartnerMode.getPartner().getId().equals(
                                            apec.getSinisterPec().getSinister().getContract().getClient().getId())
                                    && userExtra.getPersonId().equals(
                                            apec.getSinisterPec().getSinister().getContract().getAgence().getId())) {
                                listFiltredByProfil.add(apec);
                            }
                        }
                    }
                } else if (userExtra.getProfile().getId().equals(5L)) {
                    for (Apec apec : list) {
                        if (apec.getSinisterPec().getAssignedTo() != null) {

                            if (apec.getSinisterPec().getAssignedTo().getId().equals(userExtra.getId())) {

                                listFiltredByProfil.add(apec);

                            }
                        }
                    }
                    Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(userExtra.getId());
                    for (UserExtra usr : usersChild) {
                        for (Apec apec : list) {
                            if (apec.getSinisterPec().getAssignedTo() != null) {
                                if (usr.getUser().getId().equals(apec.getSinisterPec().getAssignedTo().getId())) {
                                    listFiltredByProfil.add(apec);
                                }
                            }
                        }
                    }

                } else if (userExtra.getProfile().getId().equals(4L)) {
                    for (Apec apec : list) {
                        if (apec.getSinisterPec().getAssignedTo() != null) {

                            if (apec.getSinisterPec().getAssignedTo().getId().equals(userExtra.getId())) {

                                listFiltredByProfil.add(apec);

                            }
                        }
                    }
                    Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(userExtra.getId());
                    for (UserExtra usrCh : usersChild) {
                        for (Apec apec : list) {
                            if (apec.getSinisterPec().getAssignedTo() != null) {

                                if (apec.getSinisterPec().getAssignedTo().getId().equals(usrCh.getId())) {

                                    listFiltredByProfil.add(apec);

                                }
                            }
                        }
                        Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
                        for (UserExtra usr : usersChilds) {
                            for (Apec apec : list) {
                                if (apec.getSinisterPec().getAssignedTo() != null) {
                                    if (usr.getUser().getId().equals(apec.getSinisterPec().getAssignedTo().getId())) {
                                        listFiltredByProfil.add(apec);
                                    }
                                }
                            }
                        }
                    }

                } else {
                    for (Apec apec : list) {
                        if (apec.getSinisterPec().getAssignedTo() != null) {

                            if (apec.getSinisterPec().getAssignedTo().getId().equals(userExtra.getId())) {

                                listFiltredByProfil.add(apec);

                            }
                        }
                    }
                }
            }
            return apecMapper.toDto(listFiltredByProfil);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Get one apec by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ApecDTO findOne(Long id) {
        log.debug("Request to get Apec : {}", id);
        Apec apec = apecRepository.findOne(id);
        System.out.println("testApecObservationObject");
        System.out.println(apec.toString());
        return apecMapper.toDto(apec);
    }

    @Override
    @Transactional(readOnly = true)
    public ApecDTO findAccordByQuotation(Long id) {

        log.debug("Request to get Apec By Quotation: {}", id);
        Apec apec = apecRepository.findAccordByQuotation(id);
        return apecMapper.toDto(apec);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ApecDTO> findListAccordByQuotation(Long id) {

        log.debug("Request to get Apec By Quotation: {}", id);
        Set<Apec> apecs = apecRepository.findAccordListByQuotation(id);
        if (CollectionUtils.isNotEmpty(apecs)) {
            return apecs.stream().map(apecMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ApecDTO> findByStateAccord(Long id, Integer etat) {
        log.debug("Request to get all Apecs");
        Set<Apec> apecs = apecRepository.findSinisterPecAccordByEtat(etat);
        Set<Apec> sinistersPecByUser = new HashSet<>();
        UserExtra userExtra = userExtraRepository.findByUser(id);
        Integer size = userExtra.getUserPartnerModes().size();
        if (size.equals(0)) {
            if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
                for (Apec apec : apecs) {
                    if (userExtra.getPersonId()
                            .equals(apec.getSinisterPec().getSinister().getContract().getClient().getId())) {
                        sinistersPecByUser.add(apec);
                    }
                }
            } else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
                for (Apec apec : apecs) {
                    if (userExtra.getPersonId()
                            .equals(apec.getSinisterPec().getSinister().getContract().getAgence().getId())) {
                        sinistersPecByUser.add(apec);
                    }
                }
            } else if (userExtra.getProfile().getId().equals(28L)) {
                for (Apec apec : apecs) {
                	if(apec.getSinisterPec().getReparateur() != null) {
                		if (userExtra.getPersonId().equals(apec.getSinisterPec().getReparateur().getId())) {
                            sinistersPecByUser.add(apec);
                        }
                	}
                }
            } else {
                sinistersPecByUser = apecs;
            }

        } else {
            if (userExtra.getProfile().getId().equals(23L) || userExtra.getProfile().getId().equals(24L)) {
                for (UserPartnerMode userPartnerMode : userExtra.getUserPartnerModes()) {
                    for (Apec apec : apecs) {
                        if (userPartnerMode.getModeGestion().getId().equals(apec.getSinisterPec().getMode().getId())
                                && userPartnerMode.getPartner().getId()
                                        .equals(apec.getSinisterPec().getSinister().getContract().getClient().getId())
                                && userExtra.getPersonId().equals(
                                        apec.getSinisterPec().getSinister().getContract().getAgence().getId())) {
                            sinistersPecByUser.add(apec);
                        }
                    }
                }
            } else if (userExtra.getProfile().getId().equals(5L)) {
                for (Apec sinPec : apecs) {
                    if (sinPec.getSinisterPec().getAssignedTo() != null) {

                        if (sinPec.getSinisterPec().getAssignedTo().getId().equals(id)) {

                            sinistersPecByUser.add(sinPec);

                        }
                    }
                }
                Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(id);
                for (UserExtra usr : usersChild) {
                    for (Apec sinPec : apecs) {
                        if (sinPec.getSinisterPec().getAssignedTo() != null) {
                            if (usr.getUser().getId().equals(sinPec.getSinisterPec().getAssignedTo().getId())) {
                                sinistersPecByUser.add(sinPec);
                            }
                        }
                    }
                }

            } else if (userExtra.getProfile().getId().equals(4L)) {
                for (Apec sinPec : apecs) {
                    if (sinPec.getSinisterPec().getAssignedTo() != null) {

                        if (sinPec.getSinisterPec().getAssignedTo().getId().equals(id)) {

                            sinistersPecByUser.add(sinPec);

                        }
                    }
                }
                Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(id);
                for (UserExtra usrCh : usersChild) {
                    for (Apec sinPec : apecs) {
                        if (sinPec.getSinisterPec().getAssignedTo() != null) {

                            if (sinPec.getSinisterPec().getAssignedTo().getId().equals(usrCh.getId())) {

                                sinistersPecByUser.add(sinPec);

                            }
                        }
                    }
                    Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
                    for (UserExtra usr : usersChilds) {
                        for (Apec sinPec : apecs) {
                            if (sinPec.getSinisterPec().getAssignedTo() != null) {
                                if (usr.getUser().getId().equals(sinPec.getSinisterPec().getAssignedTo().getId())) {
                                    sinistersPecByUser.add(sinPec);
                                }
                            }
                        }
                    }
                }

            } else if (userExtra.getProfile().getId().equals(6L) || userExtra.getProfile().getId().equals(7L)) {
                for (Apec sinPec : apecs) {
                    if (sinPec.getSinisterPec().getAssignedTo() != null) {

                        if (sinPec.getSinisterPec().getAssignedTo().getId().equals(id)) {

                            sinistersPecByUser.add(sinPec);

                        }
                    }
                }
            } else {
                sinistersPecByUser = apecs;
            }
        }

        if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
            return sinistersPecByUser.stream().map(apecMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();

    }

    @Transactional(readOnly = true)
    public Set<SinisterPecDTO> findAllSinPecWithValidAccord(Long id) {
        log.debug("Request to get all sinisters pec");
        Set<Apec> apecs = apecRepository.findAllValidAccord();
        Set<SinisterPec> sinistersPecWithAccordValid = new HashSet<>();
        for (Apec apec : apecs) {
            SinisterPec sinisterPec = sinisterPecRepository.findOne(apec.getSinisterPec().getId());
            sinistersPecWithAccordValid.add(sinisterPec);
        }

        Set<SinisterPec> sinistersPecByUser = new HashSet<>();
        UserExtra userExtra = userExtraRepository.findByUser(id);
        Integer size = userExtra.getUserPartnerModes().size();
        if (size.equals(0)) {
            sinistersPecByUser = sinistersPecWithAccordValid;
        } else {
            if (userExtra.getProfile().getId().equals(5L)) {
                Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(id);
                for (UserExtra usr : usersChild) {
                    for (SinisterPec sinPec : sinistersPecWithAccordValid) {
                        if (sinPec.getAssignedTo() != null) {
                            if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
                                sinistersPecByUser.add(sinPec);
                            }
                        }
                    }
                }

            } else if (userExtra.getProfile().getId().equals(4L)) {
                Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(id);
                for (UserExtra usr : usersChild) {
                    for (SinisterPec sinPec : sinistersPecWithAccordValid) {
                        if (sinPec.getAssignedTo() != null) {
                            if (usr.getUser().getId().equals(sinPec.getAssignedTo().getId())) {
                                sinistersPecByUser.add(sinPec);
                            }
                        }
                    }
                }

            } else {
                for (SinisterPec sinPec : sinistersPecWithAccordValid) {
                    if (sinPec.getAssignedTo() != null) {

                        if (sinPec.getAssignedTo().getId().equals(id)) {

                            sinistersPecByUser.add(sinPec);

                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
            return sinistersPecByUser.stream().map(sinisterPecMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Transactional(readOnly = true)
    public Set<ApecDTO> findValidAccordBySinPec(Long id) {
        log.debug("Request to get Apec : {}", id);
        Set<Apec> apecs = apecRepository.findValidAccordBySinPec(id);
        if (CollectionUtils.isNotEmpty(apecs)) {
            return apecs.stream().map(apecMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Transactional(readOnly = true)
    public Set<ApecDTO> findValidAccordBySinPecForBonSortie(Long id) {
        log.debug("Request to get Apec : {}", id);
        Set<Apec> apecs = apecRepository.findValidAccordBySinPecForBonSortie(id);
        if (CollectionUtils.isNotEmpty(apecs)) {
            return apecs.stream().map(apecMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * Delete the apec by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Apec : {}", id);
        apecRepository.delete(id);
        apecSearchRepository.delete(id);
    }

    /**
     * Search for the apec corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApecDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Apecs for query {}", query);
        Page<Apec> result = apecSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(apecMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findLastApecNumber() {
        return apecRepository.findLastApecNumber();
    }

    @Override
    @Transactional(readOnly = true)
    public ApecDTO findAccordBySinPecAndEtat(Long id, Integer etat) {

        log.debug("Request to get Apec By etat : {}", id, etat);
        System.out.println("id" + id + "etat" + etat);
        Apec apec = apecRepository.findAccordBySinPecAndEtat(id, etat);
        // System.out.println("apec id"+apec.getId());

        return apecMapper.toDto(apec);
    }

    @Override
    @Transactional(readOnly = true)
    public ApecDTO findAccordBySinPecAndEtatFixe(Long id) {

        log.debug("Request to get Apec By etat : {}", id);
        System.out.println("id" + id);
        Apec apec = apecRepository.findAccordBySinPecAndEtatFixe(id);
        // System.out.println("apec id"+apec.getId());

        return apecMapper.toDto(apec);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ApecDTO> findAccordBySinPec(Long id) {

        log.debug("Request to get Apec By etat : {}", id);
        Set<Apec> apecs = apecRepository.findAccordBySinPec(id);
        if (CollectionUtils.isNotEmpty(apecs)) {
            return apecs.stream().map(apecMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ApecDTO> findAccordByIdDevis(Long quotationId) {

        log.debug("Request to get Apec By etat : {}", quotationId);
        Set<Apec> apec = apecRepository.findAccordByIdDevis(quotationId);
        if (CollectionUtils.isNotEmpty(apec)) {
            return apec.stream().map(apecMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}
