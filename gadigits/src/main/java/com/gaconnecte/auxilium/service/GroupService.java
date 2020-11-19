
package com.gaconnecte.auxilium.service;

import java.util.List;

import com.gaconnecte.auxilium.service.dto.GroupDTO;

public interface GroupService {

  GroupDTO save(GroupDTO groupDTO);

  GroupDTO update(GroupDTO groupDTO);

  List<GroupDTO> findAll();
  
  GroupDTO findOne(Long id);
  Long findProductIdByGroup(Long id);

   void deleteGroup(Long id);



}