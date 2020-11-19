package com.gaconnecte.auxilium.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;
import com.gaconnecte.auxilium.service.dto.TodoPrestationPecDTO;

import io.swagger.annotations.ApiParam;

/**
 * Service Interface for managing PrestationPEC.
 */
public interface PrestationPECService {

	/**
	 * Save a prestationPEC.
	 *
	 * @param prestationPECDTO the entity to save
	 * @return the persisted entity
	 */
	PrestationPECDTO save(PrestationPECDTO prestationPECDTO);

	/**
	 * Save a prestationPEC Files .
	 *
	 * @param prestationPECDTO Attachments the entity to save
	 * @return the persisted entity
	 */
	PrestationPECDTO saveAttachments(MultipartFile[] prestationFiles, Long id);

	ArrayList<AttachmentDTO> findAttachments(String entityName, Long entityId);
	
	Page<AttachmentDTO> findAttachments(Pageable pageable, Long id);

	/**
	 * Get all the prestationPECS.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	Page<PrestationPECDTO> findAll(Pageable pageable);

	/**
	 * Get all the PrestationPECDTO where AffetctExpert is null.
	 *
	 * @return the list of entities
	 */
	List<PrestationPECDTO> findAllWhereAffetctExpertIsNull();

	/**
	 * get all the prestationPECS when tiers is not null.
	 * 
	 * @return the list of entities
	 */
	public Page<PrestationPECDTO> findAllPrestationPECWhenTiersIsNotNull(Long id, @ApiParam Pageable pageable);

	/**
	 * Get all the PrestationPECDTO where AffectReparateur is null.
	 *
	 * @return the list of entities
	 */
	List<PrestationPECDTO> findAllWhereAffectReparateurIsNull();

	/**
	 * Get the "id" prestationPEC.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	PrestationPECDTO findOne(Long id);

	/**
	 * Delete the "id" prestationPEC.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);

	PrestationPECDTO findPECByDossier(Long dossierId);

	PrestationPECDTO findPrestationPECByReference(String reference);

	Page<PrestationPECDTO> search(String query, Pageable pageable);

	/**
	 * added by sofien
	 *
	 * get all the prestationPECS where AffectReparateur is not null.
	 * 
	 * @return the list of entities
	 */

	List<PrestationPECDTO> findAllWhereAffectReparateurIsNotNull();

	Page<PrestationPECDTO> findAllPrestationPECAcceptedRepNullExpertNull(Pageable pageable);

	Page<PrestationPECDTO> findAllPrestationPECWithGenerateAccordStatusForDevis(Pageable pageable);

	/**
	 * All prestationPEC Accepted OR witth inprogress status Quotation
	 * 
	 * @param pageable
	 * @return
	 */
	Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationInProgress(Pageable pageable);

	/**
	 * All prestationPEC Accepted OR witth refused status Quotation
	 * 
	 * @param pageable
	 * @return
	 */
	Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationRefused(Pageable pageable);

	/**
	 * All prestationPEC Accepted OR witth accord status Quotation
	 * 
	 * @param pageable
	 * @return
	 */
	Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationAccord(Pageable pageable);

	/**
	 * All prestationPEC Accepted OR witth generated accord status Quotation
	 * 
	 * @param pageable
	 * @return
	 */
	Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationGeneratedAccord(Pageable pageable);

	Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationSignAccord(Pageable pageable);

	Page<PrestationPECDTO> findAllAcceptedPrestationPECOrQuaotationValidBill(Pageable pageable);

	Page<PrestationPECDTO> findAllPrestationPECCanceled(Pageable pageable);

	Page<PrestationPECDTO> findAllPrestationPECAccepted(Pageable pageable);

	Page<PrestationPECDTO> findAllPrestationPECInProgress(Pageable pageable);

	Page<PrestationPECDTO> findPrestationPECWithReserve(Pageable pageable);

	Page<PrestationPECDTO> findAllPrestationPECAcceptedExpertAndReparateurNotNull(Pageable pageable);

	/**
	 * get all dossiers not accepted.
	 * 
	 * @return the list of entities.
	 */
	public AttachmentDTO updateAttachmentPEC(MultipartFile file, Long id, String label);

	List<PrestationPECDTO> findAllWherePrestationNotAccepted();

	List<PrestationPECDTO> findPrestationPECsByReparateur(Long id, Long operation);

	List<PrestationPECDTO> findPrestationPECsByExpert(Long id, Long operation);

	List<PrestationPECDTO> findPrestationPECsByUserId(Long id);

	List<PrestationPECDTO> findPrestationPECsByCompagnieId(Long id);

	public AttachmentDTO saveAttachmentPEC(MultipartFile file, Long id, String label);

	public Page<AttachmentDTO> attachmentsByPrestationPEC(Pageable pageable, Long id);

	/**
	 * Get count of pec prestation
	 * 
	 * @return the number of pec prestation
	 */
	Long getCountPrestationPec(String debut, String fin);

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
	public TodoPrestationPecDTO findInProgressPrestationByReference(String reference);
}