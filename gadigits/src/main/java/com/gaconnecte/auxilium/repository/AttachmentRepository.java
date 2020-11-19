package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.app.Attachment;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Attachment entity.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:id ")
    Page<Attachment> findAttachments(Pageable pageable,@Param("id") Long id);
    
    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:id and attachment.entityName = 'imprimePec' ")
    Set<Attachment> findImprimeAttachments(@Param("id") Long id);
    
    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:id and attachment.entityName = 'autresPiecesJointes' ")
    Set<Attachment> findAutresPiecesAttachments(@Param("id") Long id);
    
    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:id and attachment.entityName = 'PHOTOREPARATION' ")
    Set<Attachment>findReparationAttachments(@Param("id") Long id);
    
    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:id and attachment.entityName = 'EXPERTISE' ")
    Set<Attachment>findExpertiseAttachments(@Param("id") Long id);
    
    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:id and attachment.entityName = 'bonSortie' ")
    Attachment findBonSortieAttachments(@Param("id") Long id);
    
    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:id and attachment.entityName = 'ordreMission' ")
    List<Attachment> findOrdreMissionAttachments(@Param("id") Long id);
 
    @Query("select  attachment from Attachment attachment where attachment.entityId =:entityId and attachment.entityName =:entityName and  attachment.id = (select MAX(id)  from Attachment attachment where attachment.entityId =:entityId and attachment.entityName =:entityName) ")
    Attachment  findAttachmentsByEntity(@Param("entityName") String entityName ,@Param("entityId") Long entityId);
    
    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:entityId and attachment.entityName =:entityName ")
    Set<Attachment>  findAttachments(@Param("entityName") String entityName ,@Param("entityId") Long entityId);
    
    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:id and attachment.label = 'GTEstimate' ")
    List<Attachment> findGTAttachments(@Param("id") Long id);
    
    @Query("select  attachment from Attachment attachment where attachment.entityId =:entityId and attachment.entityName =:entityName")
    Attachment  findAttachmentsByEntityIdAndEntityName(@Param("entityName") String entityName ,@Param("entityId") Long entityId);

    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:entityId and attachment.entityName =:entityName and attachment.label =:label ")
    Set<Attachment>  findAttachmentsByEntityAndEntityNameAndLabel(@Param("entityName") String entityName ,@Param("entityId") Long entityId, @Param("label") String label);
    
    @Query("select distinct attachment from Attachment attachment where attachment.entityId =:id and attachment.entityName = 'PLUSPHOTOPEC' ")
    Set<Attachment>findPlusDossiersAttachments(@Param("id") Long id);

}
