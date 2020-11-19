package com.gaconnecte.auxilium.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gaconnecte.auxilium.service.dto.GroupDTO;
import com.gaconnecte.auxilium.service.mapper.GroupMapper;
import com.gaconnecte.auxilium.domain.Group;
import com.gaconnecte.auxilium.repository.GroupRepository;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.GroupDetailsRepository;
import com.gaconnecte.auxilium.service.GroupService;


@Service
@Transactional
public class GroupServiceImpl implements GroupService{

  private final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);


    private final GroupRepository groupRepository;  
	private final UserRepository userRepository;
	private final GroupDetailsRepository groupDetailsRepository;    
    private final GroupMapper groupMapper;



    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper,GroupDetailsRepository groupDetailsRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
		this.groupDetailsRepository = groupDetailsRepository;
		this.userRepository = userRepository;
        this.groupMapper = groupMapper;
       
    }

	@Override
	public GroupDTO save(GroupDTO groupDTO) {
		
		log.debug("Request to save Group : {}", groupDTO);
        Group group = groupMapper.toEntity(groupDTO);
        log.debug("before repository save : {}", groupDTO);
        group = groupRepository.save(group);
        log.debug("after repository save  : {}", group.getId());
        GroupDTO result = groupMapper.toDto(group);
        
        return result;
	}

	@Override
	public GroupDTO update(GroupDTO groupDTO) {
		
		return null;
	}

	@Override
	public List<GroupDTO> findAll() {
		log.debug("Request to get all Group");
		return groupMapper.toDto(groupRepository.findAll());
        
	}

	@Override
	public GroupDTO findOne(Long id) {
		
		log.debug("Request to get Group : {}", id);
        Group group = groupRepository.findOne(id);
        return groupMapper.toDto(group);
	}

	@Override
	public Long findProductIdByGroup(Long id) {
		
		return groupRepository.findProductIdByGroup(id);
	}

	@Override

	public void deleteGroup(Long idGroup) {
		
		userRepository.updateGroup(idGroup);
		groupDetailsRepository.deleteGroupDetails(idGroup);
		groupRepository.delete(idGroup);
	}



}