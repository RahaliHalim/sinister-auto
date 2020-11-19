package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.app.SinisterPrestation;
import com.gaconnecte.auxilium.service.dto.SinisterPrestationDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefStatusSinisterMapper;

/**
 * Mapper for the entity SinisterPrestation and its DTO SinisterPrestationDTO.
 */
@Mapper(componentModel = "spring", uses = {SinisterMapper.class, RaisonAssistanceMapper.class, RefTypeServiceMapper.class, RefStatusSinisterMapper.class, GovernorateMapper.class, DelegationMapper.class, UserMapper.class, ObservationMapper.class})
public interface SinisterPrestationMapper extends EntityMapper <SinisterPrestationDTO, SinisterPrestation> {

    @Mapping(source = "sinister.id", target = "sinisterId")
    @Mapping(source = "sinister.reference", target = "reference")
    @Mapping(source = "sinister.incidentDate", target = "incidentDate")
    @Mapping(source = "sinister.vehicle.immatriculationVehicule", target = "vehicleRegistration")
    @Mapping(source = "sinister.insured.fullName", target = "insuredName")
        
    @Mapping(source = "incidentGovernorate.id", target = "incidentGovernorateId")
    @Mapping(source = "incidentGovernorate.label", target = "incidentGovernorateLabel")

    @Mapping(source = "incidentLocation.id", target = "incidentLocationId")
    @Mapping(source = "incidentLocation.label", target = "incidentLocationLabel")

    @Mapping(source = "destinationGovernorate.id", target = "destinationGovernorateId")
    @Mapping(source = "destinationGovernorate.label", target = "destinationGovernorateLabel")

    @Mapping(source = "destinationLocation.id", target = "destinationLocationId")
    @Mapping(source = "destinationLocation.label", target = "destinationLocationLabel")
    
   // @Mapping(source = "deliveryGovernorate.id", target = "deliveryGovernorateId")
  // @Mapping(source = "deliveryGovernorate.label", target = "deliveryGovernorateLabel")

   // @Mapping(source = "deliveryLocation.id", target = "deliveryLocationId")
   // @Mapping(source = "deliveryLocation.label", target = "deliveryLocationLabel")
    
    
    @Mapping(source = "creationUser.id", target = "creationUserId")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.label", target = "statusLabel")            

    @Mapping(source = "serviceType.id", target = "serviceTypeId")
    @Mapping(source = "serviceType.nom", target = "serviceTypeLabel")
            
    @Mapping(source = "cancelGrounds.id", target = "cancelGroundsId")
    @Mapping(source = "cancelGrounds.label", target = "cancelGroundsDescription")
    @Mapping(source = "reopenGrounds.id", target = "reopenGroundsId")
    @Mapping(source = "reopenGrounds.label", target = "reopenGroundsDescription")
    @Mapping(source = "notEligibleGrounds.id", target = "notEligibleGroundsId")
    @Mapping(source = "notEligibleGrounds.label", target = "notEligibleGroundsDescription")
    //@Mapping(source = "motifRefus.id", target = "motifRefusId")
   // @Mapping(source = "motifRefus.label", target = "motifRefusLabel")



    SinisterPrestationDTO toDto(SinisterPrestation sinisterPrestation); 

    @Mapping(source = "sinisterId", target = "sinister")
    
    @Mapping(source = "incidentGovernorateId", target = "incidentGovernorate")

    @Mapping(source = "incidentLocationId", target = "incidentLocation")

    @Mapping(source = "destinationGovernorateId", target = "destinationGovernorate")

    @Mapping(source = "destinationLocationId", target = "destinationLocation")
    
    //@Mapping(source = "deliveryGovernorateId", target = "deliveryGovernorate")

   // @Mapping(source = "deliveryLocationId", target = "deliveryLocation")

    @Mapping(source = "creationUserId", target = "creationUser")
    
    @Mapping(source = "assignedToId", target = "assignedTo")
    
    @Mapping(source = "statusId", target = "status")

    @Mapping(source = "serviceTypeId", target = "serviceType")
            
    @Mapping(source = "cancelGroundsId", target = "cancelGrounds")
    
    @Mapping(source = "reopenGroundsId", target = "reopenGrounds")

    @Mapping(source = "notEligibleGroundsId", target = "notEligibleGrounds")
    //@Mapping(source = "motifRefusId", target = "motifRefus")

            
    SinisterPrestation toEntity(SinisterPrestationDTO sinisterPrestationDTO); 
    default SinisterPrestation fromId(Long id) {
        if (id == null) {
            return null;
        }
        SinisterPrestation sinisterPrestation = new SinisterPrestation();
        sinisterPrestation.setId(id);
        return sinisterPrestation;
    }
}
