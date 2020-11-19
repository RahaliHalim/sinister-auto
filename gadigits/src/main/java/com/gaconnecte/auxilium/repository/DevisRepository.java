package com.gaconnecte.auxilium.repository;

import com.gaconnecte.auxilium.domain.Devis;
import com.gaconnecte.auxilium.domain.PrestationPEC;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.ZonedDateTime;

/**
 * Spring Data JPA repository for the Devis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DevisRepository extends JpaRepository<Devis,Long> {
    @Query("select devis from Devis devis where devis.prestation.id =:prestationId")
    List<Devis> findDevisByPrestation(@Param("prestationId") Long prestationId);

    @Query("select devis from Devis devis where devis.prestation.id =:prestationId")
    Devis findQuotationByPrestation(@Param("prestationId") Long prestationId);

    @Query("select devis from Devis devis where devis.etatDevis = 'Soumis' and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
    List<Devis> findDevisSoumisByPrestation(@Param("prestationId") Long prestationId);

    @Query("select devis from Devis devis where (devis.etatDevis = 'Accepte' or devis.etatDevis = 'Valide' or devis.etatDevis = 'Refuse') and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
    List<Devis> findDevisAccepteOuValideOuRefuse(@Param("prestationId") Long prestationId);  

    @Query("select devis from Devis devis where devis.etatDevis = 'En_cours' and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
    List<Devis> findDevisSauvegardeByPrestation(@Param("prestationId") Long prestationId);  

    @Query("select devis from Devis devis where devis.etatDevis = 'Valide' and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
    List<Devis> findDevisValideByPrestation(@Param("prestationId") Long prestationId);  

    @Query("select devis from Devis devis where devis.etatDevis = 'Accepte' and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
    List<Devis> findDevisAccepteByPrestation(@Param("prestationId") Long prestationId);

    @Query("select devis from Devis devis where devis.etatDevis = 'Refuse' and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
    List<Devis> findDevisRefuseByPrestation(@Param("prestationId") Long prestationId); 

    @Query("select devis from Devis devis where devis.etatDevis = 'Facture' and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
    List<Devis> findDevisFactureByPrestation(@Param("prestationId") Long prestationId); 

     @Query("select devis from Devis devis where devis.etatDevis = 'Generation_bon_sortie_accepte' and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
    List<Devis> findDevisGenereBonSortieByPrestation(@Param("prestationId") Long prestationId); 

      @Query("select devis from Devis devis where devis.etatDevis = 'Refuse_Apres_Facture' and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
    List<Devis> findDevisRefusAferFactureByPrestation(@Param("prestationId") Long prestationId);  
      
      @Query("select devis from Devis devis where devis.prestation.id =:prestationId and devis.id="
      		+ "(select max(devis.id) from Devis devis where devis.prestation.id =:prestationId)")
      List<Devis> findLastDevisBySinister(@Param("prestationId") Long prestationId);

      
      @Query("select devis from Devis devis where  devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
      List<Devis> findLastDevisSauvegardeRefuseValidGes(@Param("prestationId") Long prestationId); 
      @Query("select devis from Devis devis where (devis.etatDevis = 'ValideGestionnaire') and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
      List<Devis> findLastDevisValidGes(@Param("prestationId") Long prestationId); 
      @Query("select devis from Devis devis where (devis.etatDevis = 'Valide') and devis.prestation.id =:prestationId ORDER BY devis.dateGeneration desc")
      List<Devis> findLastDevisValide(@Param("prestationId") Long prestationId);
      
    @Query("SELECT count(*) from Devis devis where devis.dateGeneration between :convertedDateD and :convertedDateF")
	  Long countDevis(@Param("convertedDateD") ZonedDateTime convertedDateD,  @Param("convertedDateF") ZonedDateTime convertedDateF);
      
   

    
    @Query("select devis.prestation from Devis devis where  devis.quotationStatus.id =:idStatus")
    List<PrestationPEC> findPrestationByStatusQuotation(@Param("idStatus") Long idStatus); 
}
