package com.gaconnecte.auxilium.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gaconnecte.auxilium.service.dto.GroupDetailDTO;
import com.gaconnecte.auxilium.service.mapper.GroupDetailsMapper;
import com.gaconnecte.auxilium.domain.Dossier;
import com.gaconnecte.auxilium.domain.GroupDetails;
import com.gaconnecte.auxilium.repository.GroupDetailsRepository;
import com.gaconnecte.auxilium.service.GroupDetailsService;


@Service
@Transactional
public class GroupDetailsServiceImpl implements GroupDetailsService{



   private final Logger log = LoggerFactory.getLogger(GroupDetailsServiceImpl.class);


    private final GroupDetailsRepository groupDetailsRepository;    
    private final GroupDetailsMapper groupDetailsMapper;

    public GroupDetailsServiceImpl(GroupDetailsRepository groupDetailsRepository, GroupDetailsMapper groupDetailsMapper) {
        this.groupDetailsRepository = groupDetailsRepository;
        this.groupDetailsMapper = groupDetailsMapper;
       
    }

	@Override
	public GroupDetailDTO save(GroupDetailDTO groupDetailsDTO) {

		log.debug("Request to save Group details : {}", groupDetailsDTO);
        GroupDetails groupDetails = groupDetailsMapper.toEntity(groupDetailsDTO);
        groupDetails = groupDetailsRepository.save(groupDetails);
        GroupDetailDTO result = groupDetailsMapper.toDto(groupDetails);
        
        return result;
	}

	@Override
	public GroupDetailDTO update(GroupDetailDTO groupDetailsDTO) {
		
		return null;
	}

	@Override
	public List<GroupDetailDTO> findAll() {
		log.debug("Request to get all Group details");
        return groupDetailsMapper.toDto(groupDetailsRepository.findAll());
        
	}

	@Override
	@Transactional
	public GroupDetailDTO findOne(Long id) {
		
        log.debug("Request to get GroupDetails : {}", id);
        GroupDetails groupdetails = groupDetailsRepository.findOne(id);
        return groupDetailsMapper.toDto(groupdetails);
	}

@Override
	public List<GroupDetailDTO> findByGroupId(Long id) {
		return groupDetailsMapper.toDto(groupDetailsRepository.findByGroupId(id));
	}
	@Override

	public void delete(Long idGroup) {
		
		groupDetailsRepository.deleteGroupDetails(idGroup);
	}

    @Override

	public void deleteGroupDetails(Long idGroup) {
		
		groupDetailsRepository.delete(idGroup);
	}
	

}