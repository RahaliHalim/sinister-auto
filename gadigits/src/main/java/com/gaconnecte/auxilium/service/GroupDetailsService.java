package com.gaconnecte.auxilium.service;

import java.util.List;

import com.gaconnecte.auxilium.service.dto.GroupDetailDTO;

public interface GroupDetailsService {

  GroupDetailDTO save(GroupDetailDTO groupDetailsDTO);

  GroupDetailDTO update(GroupDetailDTO groupDetailsDTO);
  
  GroupDetailDTO findOne(Long id);
  
  void delete(Long idGroup);

  List<GroupDetailDTO> findAll();
  
  List<GroupDetailDTO> findByGroupId(Long id);

  void deleteGroupDetails(Long id);

}