package com.gaconnecte.auxilium.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.app.Sinister;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.referential.mapper.RefStatusSinisterMapper;

/**
 * Mapper for the entity Sinister and its DTO SinisterDTO.
 */
@Mapper(componentModel = "spring", uses = {SinisterPecMapper.class, SinisterPrestationMapper.class, RefStatusSinisterMapper.class, GovernorateMapper.class, DelegationMapper.class, VehiculeAssureMapper.class, UserMapper.class, PartnerMapper.class, ContratAssuranceMapper.class, AssureMapper.class,RefNaturePanneMapper.class})
public interface SinisterMapper extends EntityMapper <SinisterDTO, Sinister> {

    @Mapping(source = "governorate.id", target = "governorateId")
    @Mapping(source = "governorate.label", target = "governorateLabel")

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.label", target = "locationLabel")

    @Mapping(source = "partner.id", target = "partnerId")
    @Mapping(source = "partner.companyName", target = "partnerLabel")

    @Mapping(source = "creationUser.id", target = "creationUserId")
    
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "insured.nom", target = "insuredName")
    @Mapping(source = "insured.prenom", target = "insuredSurName")
    @Mapping(source = "insured.premTelephone", target = "tel")
    @Mapping(source = "vehicle.usage.label", target = "usageLabel")
    
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.immatriculationVehicule", target = "vehicleRegistration")            

    @Mapping(source = "insured.id", target = "insuredId")
    @Mapping(source = "insured.fullName", target = "insuredFullName")
    
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.label", target = "statusLabel")            
    @Mapping(source = "naturePanne.id", target = "naturePanneId")

    SinisterDTO toDto(Sinister sinister); 
    
    @Mapping(source = "governorateId", target = "governorate")

    @Mapping(source = "locationId", target = "location")
    
    @Mapping(source = "partnerId", target = "partner")

    @Mapping(source = "creationUserId", target = "creationUser")

    @Mapping(source = "contractId", target = "contract")
    
    @Mapping(source = "vehicleId", target = "vehicle")
    
    @Mapping(source = "insuredId", target = "insured")
    
    @Mapping(source = "statusId", target = "status")

    @Mapping(source = "naturePanneId", target = "naturePanne")

    Sinister toEntity(SinisterDTO sinisterDTO); 
    default Sinister fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sinister sinister = new Sinister();
        sinister.setId(id);
        return sinister;
    }
}
