package com.gaconnecte.auxilium.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.UserExtra;
import com.gaconnecte.auxilium.domain.UserPartnerMode;
import com.gaconnecte.auxilium.domain.ViewApec;
import com.gaconnecte.auxilium.repository.UserExtraRepository;
import com.gaconnecte.auxilium.repository.ViewApecRepository;
import com.gaconnecte.auxilium.service.dto.ViewApecDTO;
import com.gaconnecte.auxilium.service.mapper.ViewApecMapper;

@Service
@Transactional
public class ViewApecService {
	
	private final Logger log = LoggerFactory.getLogger(ViewApecService.class);
	
	private final ViewApecRepository viewApecRepository;

	private final ViewApecMapper viewApecMapper;
	
	@Autowired
	private UserExtraRepository userExtraRepository;
	
	public ViewApecService(ViewApecRepository viewApecRepository, ViewApecMapper viewApecMapper) {
		this.viewApecMapper = viewApecMapper;
		this.viewApecRepository = viewApecRepository;
	}
	
	@Transactional(readOnly = true)
    public Set<ViewApecDTO> findByStateAccord(Long id, Integer etat) {
        log.debug("Request to get all Apecs");
        Long stepPecId = 110L;
        Set<ViewApec> apecs = viewApecRepository.findSinisterPecAccordByEtat(etat,stepPecId);
        Set<ViewApec> sinistersPecByUser = new HashSet<>();
        UserExtra userExtra = userExtraRepository.findByUser(id);
        Integer size = userExtra.getUserPartnerModes().size();
        if (size.equals(0)) {
            if (userExtra.getProfile().getId().equals(25L) || userExtra.getProfile().getId().equals(26L)) {
                for (ViewApec apec : apecs) {
                    if (userExtra.getPersonId()
                            .equals(apec.getCompanyId())) {
                        sinistersPecByUser.add(apec);
                    }
                }
            } else if (userExtra.getProfile().getId().equals(24L) || userExtra.getProfile().getId().equals(23L)) {
                for (ViewApec apec : apecs) {
                    if (userExtra.getPersonId()
                            .equals(apec.getAgencyId())) {
                        sinistersPecByUser.add(apec);
                    }
                }
            } else if (userExtra.getProfile().getId().equals(28L)) {
                for (ViewApec apec : apecs) {
                	if(apec.getReparateurId() != null) {
                		if (userExtra.getPersonId().equals(apec.getReparateurId())) {
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
                    for (ViewApec apec : apecs) {
                        if (userPartnerMode.getModeGestion().getId().equals(apec.getModeId())
                                && userPartnerMode.getPartner().getId()
                                        .equals(apec.getCompanyId())
                                && userExtra.getPersonId().equals(
                                        apec.getAgencyId())) {
                            sinistersPecByUser.add(apec);
                        }
                    }
                }
            } else if (userExtra.getProfile().getId().equals(5L)) {
                for (ViewApec sinPec : apecs) {
                    if (sinPec.getAssignedToId() != null) {

                        if (sinPec.getAssignedToId().equals(id)) {

                            sinistersPecByUser.add(sinPec);

                        }
                    }
                }
                Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(id);
                for (UserExtra usr : usersChild) {
                    for (ViewApec sinPec : apecs) {
                        if (sinPec.getAssignedToId() != null) {
                            if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
                                sinistersPecByUser.add(sinPec);
                            }
                        }
                    }
                }

            } else if (userExtra.getProfile().getId().equals(4L)) {
                for (ViewApec sinPec : apecs) {
                    if (sinPec.getAssignedToId() != null) {

                        if (sinPec.getAssignedToId().equals(id)) {

                            sinistersPecByUser.add(sinPec);

                        }
                    }
                }
                Set<UserExtra> usersChild = userExtraRepository.findUserChildToUserBoss(id);
                for (UserExtra usrCh : usersChild) {
                    for (ViewApec sinPec : apecs) {
                        if (sinPec.getAssignedToId() != null) {

                            if (sinPec.getAssignedToId().equals(usrCh.getId())) {

                                sinistersPecByUser.add(sinPec);

                            }
                        }
                    }
                    Set<UserExtra> usersChilds = userExtraRepository.findUserChildToUserBoss(usrCh.getId());
                    for (UserExtra usr : usersChilds) {
                        for (ViewApec sinPec : apecs) {
                            if (sinPec.getAssignedToId() != null) {
                                if (usr.getUser().getId().equals(sinPec.getAssignedToId())) {
                                    sinistersPecByUser.add(sinPec);
                                }
                            }
                        }
                    }
                }

            } else if (userExtra.getProfile().getId().equals(6L) || userExtra.getProfile().getId().equals(7L)) {
                for (ViewApec sinPec : apecs) {
                    if (sinPec.getAssignedToId() != null) {

                        if (sinPec.getAssignedToId().equals(id)) {

                            sinistersPecByUser.add(sinPec);

                        }
                    }
                }
            } else {
                sinistersPecByUser = apecs;
            }
        }

        if (CollectionUtils.isNotEmpty(sinistersPecByUser)) {
            return sinistersPecByUser.stream().map(viewApecMapper::toDto).collect(Collectors.toSet());
        }
        return new HashSet<>();

    }

}
