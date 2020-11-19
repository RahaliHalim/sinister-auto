package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.User;

import java.util.List;

/**
 * Created by Dell on 11/5/2017.
 */
public interface ActivitiRolesService {

    List<String> getActivitiRoles(User user);
}
