package com.gaconnecte.auxilium.service;

import com.gaconnecte.auxilium.domain.PrestationPEC;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.service.dto.ApecDTO;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.SinisterDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.service.dto.TodoPrestationPecDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiParam;
import java.sql.SQLException;

import java.util.List;
import java.util.Set;

public interface SinisterPecService {
	/**
	 * Save a prestationPEC.
	 *
	 * @param prestationPECDTO the entity to save
	 * @return the persisted entity
	 */
	SinisterPecDTO save(SinisterPecDTO sinisterPecDTO);

	/**
	 * Save a prestationPEC Files .
	 *
	 * @param prestationPECDTO Attachments the entity to save
	 * @return the persisted entity
	 */
	SinisterPecDTO saveAttachments(MultipartFile[] prestationFiles, Long id);

	Page<AttachmentDTO> findAttachments(Pageable pageable, Long id);

	Set<AttachmentDTO> findImprimeAttachments(Long id);

	Set<AttachmentDTO> findReparationAttachments(Long id);
	
	Set<AttachmentDTO> findPlusDossiersAttachments(Long id);

	/**
	 * Get all the prestationPECS.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	List<SinisterPecDTO> findAll();

	/**
	 * Get all the PrestationPECDTO where AffetctExpert is null.
	 *
	 * @return the list of entities
	 */
	List<SinisterPecDTO> findAllWhereAffetctExpertIsNull();

	/**
	 * get all the prestationPECS when tiers is not null.
	 * 
	 * @return the list of entities
	 */
	public Page<SinisterPecDTO> findAllSinisterPECWhenTiersIsNotNull(Long id, @ApiParam Pageable pageable);

	/**
	 * Get all the PrestationPECDTO where not Fected expert.
	 *
	 * @return the list of entities
	 */

	Set<SinisterPecDTO> findAllPrestationPECInCheckSupported();

	/**
	 * Get all the PrestationPECDTO For Expert Opinion .
	 *
	 * @return the list of entities
	 */
	Set<SinisterPecDTO> getSinisterPecForExpertOpinion(Long id);

	/**
	 * Get all the PrestationPECDTO For Dismantling .
	 *
	 * @return the list of entities
	 */
	Set<SinisterPecDTO> getSinisterPecForUpdateDevis(Long id);

	Set<SinisterPecDTO> getAllSinisterPecInVerification(Long id);

	Set<SinisterPecDTO> getSinisterPecForDismantling(Long id);

	/**
	 * Get all the PrestationPECDTO where in confirmation Devis.
	 *
	 * @return the list of entities
	 */
	Set<SinisterPecDTO> getSinisterPecInConfirmationDevis(Long id);

	Set<SinisterPecDTO> getSinisterPecInConfirmationDevisComplementaire();

	Set<SinisterPecDTO> getSinisterPecInRevueValidationDevis(Long id);

	/**
	 * Get all the PrestationPECDTO where Affected Expert.
	 *
	 * @return the list of entities
	 */

	Set<SinisterPecDTO> findAllPrestationPECInNotCancelExpertMission();

	/**
	 * Get all the PrestationPECDTO where AffectReparateur is null.
	 *
	 * @return the list of entities
	 */
	Set<SinisterPecDTO> findAllPrestationPECAcceptedRepNull();

	/**
	 * Get all the PrestationPECDTO where AffectReparateur is affected.
	 *
	 * @return the list of entities
	 */
	Set<SinisterPecDTO> findAllPrestationPECAcceptedAndReparateurIsAffected();

	/**
	 * Get the "id" prestationPEC.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	SinisterPecDTO findOne(Long id);

	/**
	 * Delete the "id" prestationPEC.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
	
	void deleteAttachment(Long id);

	Set<SinisterPecDTO> findPrestationPECsByReparateur(Long id);

	List<SinisterPecDTO> findPrestationPECsByReparateurComp(Long id, Long operation);

	SinisterPecDTO findPECByDossier(Long dossierId);

	SinisterPecDTO findSininsterPECByReference(String reference);

	Page<SinisterPecDTO> search(String query, Pageable pageable);

	/**
	 * added by sofien
	 *
	 * get all the prestationPECS where AffectReparateur is not null.
	 * 
	 * @return the list of entities
	 */

	List<SinisterPecDTO> findAllWhereAffectReparateurIsNotNull();

	Page<SinisterPecDTO> findAllSinisterPECAcceptedRepNullExpertNull(Pageable pageable);

	Page<SinisterPecDTO> findAllSinisterPECWithGenerateAccordStatusForDevis(Pageable pageable);

	/**
	 * All prestationPEC Accepted OR witth inprogress status Quotation
	 * 
	 * @param pageable
	 * @return
	 */
	Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationInProgress(Pageable pageable);

	/**
	 * All prestationPEC Accepted OR witth refused status Quotation
	 * 
	 * @param pageable
	 * @return
	 */
	Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationRefused(Pageable pageable);

	/**
	 * All prestationPEC Accepted OR witth accord status Quotation
	 * 
	 * @param pageable
	 * @return
	 */
	Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationAccord(Pageable pageable);

	/**
	 * All prestationPEC Accepted OR witth generated accord status Quotation
	 * 
	 * @param pageable
	 * @return
	 */
	Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationGeneratedAccord(Pageable pageable);

	Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationSignAccord(Pageable pageable);

	Page<SinisterPecDTO> findAllAcceptedSinisterPECOrQuaotationValidBill(Pageable pageable);

	Page<SinisterPecDTO> findAllSinisterPECCanceled(Pageable pageable);

	Page<SinisterPecDTO> findAllSinisterPECAccepted(Pageable pageable);

	Page<SinisterPecDTO> findAllSinisterPECInProgress(Pageable pageable);

	Page<SinisterPecDTO> findSinisterPECWithReserve(Pageable pageable);

	Page<SinisterPecDTO> findAllSinisterPECAcceptedExpertAndReparateurNotNull(Pageable pageable);

	/**
	 * get all dossiers not accepted.
	 * 
	 * @return the list of entities.
	 */
	public AttachmentDTO updateAttachmentPEC(MultipartFile file, Long id, String label);

	List<SinisterPecDTO> findAllWherePrestationNotAccepted();

	List<SinisterPecDTO> findSinisterPECsByReparateur(Long id, Long operation);

	List<SinisterPecDTO> findSinisterPECsByExpert(Long id, Long operation);

	List<SinisterPecDTO> findSinisterPECsByUserId(Long id);

	List<SinisterPecDTO> findSinisterPECsByCompagnieId(Long id);

	public AttachmentDTO saveAttachmentPEC(MultipartFile file, Long id, String label);

	public AttachmentDTO saveAttachmentPECNw(MultipartFile file, Long id, String label, String nomImage);

	public AttachmentDTO saveAttachmentQuotation(MultipartFile file, Long id, String label);

	public AttachmentDTO saveAttachmentQuotationNw(MultipartFile file, Long id, String label, String nomImage, String nomFolder);

	public Page<AttachmentDTO> attachmentsBySinisterPEC(Pageable pageable, Long id);

	public AttachmentDTO updateAttachmentQuotation(MultipartFile file, Long id, String label);

	/**
	 * Get count of pec prestation
	 * 
	 * @return the number of pec prestation
	 */
	Long getCountSinisterPec(String debut, String fin);

	Long countNumberAffeReparator(Long id);

	Long countNumberAffeExpert(Long id);

	/**
	 * Get count of all pec prestation
	 * 
	 * @return the number of all pec prestation
	 */
	public String[] getDepcAll(String debut, String fin) throws SQLException;

	/**
	 * Get prestation by it reference
	 */
	public TodoPrestationPecDTO findInProgressSinisterByReference(String reference);

	public Set<SinisterPecDTO> findAllSinisterPecToApprove(Long idUser);

	public Set<SinisterPecDTO> findAllSinisterPecBeingProcessed(Long idUser);

	public Set<SinisterPecDTO> findAllSinisterPecConsulterDemandePec(Long idUser);

	public Set<SinisterPecDTO> findAllDemandePec(Long idUser);

	public Set<SinisterPecDTO> findAllSinisterPecRefusedAndAprouveOrApprvWithModif(Long idUser);

	public Set<SinisterPecDTO> findSinisterPecByUserId(Long id);

	public Set<SinisterPecDTO> findSinisterPecByAssignedId(Long assignedToId);

	SinisterPecDTO findBySinisterId(Long id);

	public Set<SinisterPecDTO> findCanceledSinisterPec();

	public Set<SinisterPecDTO> findRefusededSinisterPec();

	public SinisterDTO findSinisterFromSinisterPec(Long id);

	public Set<SinisterPecDTO> findAllSinisterPecCanceledAndAprouveOrApprvWithModif(Long idUser);

	public Set<SinisterPecDTO> findAllSinisterPecAccWithResrveAndAprouveOrApprvWithModif(Long idUser);

	public Set<SinisterPecDTO> findAllSinisterPecAccWithChangeStatusAndAprouveOrApprvWithModif(Long idUser);

	public Set<SinisterPecDTO> findAllSinisterPecRefusedAndCanceledAndAprouveOrApprvWithModif(Long idUser);

	public Set<SinisterPecDTO> findAllPrestationPECForIdaOuverture();

	public Set<SinisterPecDTO> findAllAnnulationPrestationPec(Long idUser);

	public Set<SinisterPecDTO> findAllConfirmAnnulationPrestation(Long idUser);

	public Set<SinisterPecDTO> findAllRefusedPrestationPec(Long idUser);

	public Set<SinisterPecDTO> findAllConfirmRefusedPrestation(Long idUser);

	public Set<SinisterPecDTO> findAllSinPecWithDecision();

	public AttachmentDTO saveAttachmentFacture(MultipartFile file, Long id, String label);

	public AttachmentDTO saveAttachmentFactureNw(MultipartFile file, Long id, String label, String nomImage);

	public AttachmentDTO updateAttachmentFacture(MultipartFile file, Long id, String label);

	public AttachmentDTO saveAttachmentPieceSinisterPecPhotoReparation(MultipartFile file, Long id, String label,
			String nomImage, String nomFolder);
	
	AttachmentDTO saveAttachmentPieceSinisterPecPhotoPlus(MultipartFile file, Long id, String label,
			String nomImage);

	public AttachmentDTO saveAttachmentPieceSinisterPec(MultipartFile file, Long id, String label, String description,
			Boolean original);

	public AttachmentDTO saveAttachmentPieceSinisterPecNw(MultipartFile file, Long id, String label, String description,
			Boolean original, String nomImage);

	public AttachmentDTO saveAttachmentPieceApec(MultipartFile file, Long id, String label, String description,
			String entityName);

	public Set<SinisterPecDTO> findAllSinPecForVerificationPrinted(Long idUser);

	public AttachmentDTO saveAttachmentBonSortie(MultipartFile file, Long id, String label, String description,
			Boolean original);

	public Set<SinisterPecDTO> findAllSinPecForSignatureBonSortie(Long idUser);

	public Set<SinisterPecDTO> findAllSinPecForModificationPrestation();

	public Set<SinisterPecDTO> findAllSinPecModificationPrix();

	public AttachmentDTO findBonSortieAttachments(Long id);

	public AttachmentDTO findOrdreMissionAttachments(Long id);

	public AttachmentDTO findAttachmentByEntity(String entityName, Long entityId);

	public AttachmentDTO findGTAttachments(Long id);

	public long countSinisterPec(Long idPec);

	public AttachmentDTO saveAutresPiecesJointesFile(MultipartFile file, Long id, String label);

	public AttachmentDTO saveAutresPiecesJointesFileNw(MultipartFile file, Long id, String label, String nomImage, String note);

	public Set<AttachmentDTO> findAutresPiecesAttachments(Long id);
	
	Set<AttachmentDTO> findExpertiseAttachments(Long id);

}
