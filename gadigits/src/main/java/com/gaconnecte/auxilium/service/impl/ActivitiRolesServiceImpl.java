package com.gaconnecte.auxilium.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.gaconnecte.auxilium.domain.Authority;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.UserCellule;
import com.gaconnecte.auxilium.repository.UserCelluleRepository;
import com.gaconnecte.auxilium.service.ActivitiRolesService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dell on 11/5/2017.
 */
@Service
public class ActivitiRolesServiceImpl implements ActivitiRolesService {

    private final Logger log = LoggerFactory.getLogger(ActivitiRolesServiceImpl.class);

    @Autowired
    private UserCelluleRepository userCelluleRepository;

    @Override
    public List<String> getActivitiRoles(User user) {

        List<String> activitiRoles = new ArrayList<>();
        List<UserCellule> userCellules = userCelluleRepository.findCellulesbyUser(user.getId());
        if(CollectionUtils.isNotEmpty(userCellules)) {
            for (UserCellule uc : userCellules) {
                log.debug("[ActivitiRolesServiceImpl:getActivitiRoles] user cell : {}", uc.getCellule().getName());
                for (Authority auth : uc.getAuthorities()) {
                    log.debug("[ActivitiRolesServiceImpl:getActivitiRoles] user authority : {}", auth.getName());
                    activitiRoles.add(uc.getCellule().getName() + "_" + auth.getName());
                }
            }
        } else {
            for (Authority authority : user.getAuthorities()) {
                log.debug("[ActivitiRolesServiceImpl:getActivitiRoles] user cell : {}", authority.getName());
                activitiRoles.add(authority.getName());
            }
        }
        return activitiRoles;
    }
}
