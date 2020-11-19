package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.RefBaremeService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.RefBaremeDTO;
import com.gaconnecte.auxilium.service.impl.FileStorageService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;

import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.service.LoggerService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;


/**
 * REST controller for managing RefBareme.
 */
@RestController
@RequestMapping("/api")
public class RefBaremeResource {

    private final Logger log = LoggerFactory.getLogger(RefBaremeResource.class);

    private static final String ENTITY_NAME = "refBareme";

    private final RefBaremeService refBaremeService;

    private final FileStorageService fileStorageService;

    private LoggerService loggerService;

    @Autowired
    private HistoryService historyService;

    public RefBaremeResource(RefBaremeService refBaremeService, LoggerService loggerService, FileStorageService fileStorageService) {
        this.refBaremeService = refBaremeService;
        this.loggerService = loggerService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(value = "/ref-baremes", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<RefBaremeDTO> createRefBareme(@RequestPart(name = "croquis", required = false) MultipartFile casBareme, @RequestPart(name = "refbareme") RefBaremeDTO refBaremeDTO) throws URISyntaxException {
        log.debug("REST request to save RefBareme : {}", refBaremeDTO);
        /*if (refBaremeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new refBareme cannot already have an ID")).body(null);
        }*/
        Boolean isCreate = true;
        RefBaremeDTO oldRefBareme = new RefBaremeDTO();
        if(refBaremeDTO.getId() == null){
            isCreate = true;
        } else{
            isCreate = false;
            oldRefBareme  =  refBaremeService.findOne(refBaremeDTO.getId());
            }
        RefBaremeDTO result = refBaremeService.save(casBareme, refBaremeDTO);
        if(result!= null && isCreate == true) {
         	historyService.historysave("RefBareme", result.getId(),null, result,0,1, "Création");
        }
        if(result!= null && isCreate == false) {
         	historyService.historysave("RefBareme",result.getId(),oldRefBareme,result,0,0, "Modification");
        }
        return ResponseEntity.created(new URI("/api/ref-baremes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ref-baremes : Updates an existing refBareme.
     *
     * @param refBaremeDTO the refBaremeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refBaremeDTO,
     * or with status 400 (Bad Request) if the refBaremeDTO is not valid,
     * or with status 500 (Internal Server Error) if the refBaremeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
   /* @PutMapping("/ref-baremes")
    @Timed
    public ResponseEntity<RefBaremeDTO> updateRefBareme(@Valid @RequestBody RefBaremeDTO refBaremeDTO) throws URISyntaxException {
        log.debug("REST request to update RefBareme : {}", refBaremeDTO);
        if (refBaremeDTO.getId() == null) {
            return createRefBareme(refBaremeDTO);
        }
        RefBaremeDTO result = refBaremeService.save(refBaremeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refBaremeDTO.getId().toString()))
            .body(result);
    }*/
    
    @PostMapping(value = "/ref-baremes/attachments/{id}/{label}", consumes = { "multipart/form-data" })
	@Timed
	public ResponseEntity<AttachmentDTO> createCroquisFile(@PathVariable Long id, @PathVariable String label,
			@RequestPart(name = "croquis", required = true) MultipartFile CroquisFile) throws URISyntaxException {
		log.debug("REST request to save files Croquis ------------------------------------: {}", id);
	
		AttachmentDTO result = new AttachmentDTO();
		result = refBaremeService.saveAttachmentRefBareme(CroquisFile, id, label);
		// historyService.historysave(label, result.getId(), "CREATION");
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}
    
    @PutMapping(value = "/ref-baremes/attachments/{id}/{label}", consumes = { "multipart/form-data" })
	@Timed
	public ResponseEntity<AttachmentDTO> updateCroquisFile(@PathVariable Long id, @PathVariable String label,
			@RequestPart(name = "croquis", required = false) MultipartFile CroquisFile) throws URISyntaxException {
		log.debug("REST request to update files Croquis ------------------------------------: {}", id);
		AttachmentDTO result = new AttachmentDTO();
		result = refBaremeService.updateAttachmentRefBareme(CroquisFile, id, label);
		//historyService.historysave(label, result.getId(), "MISE A JOUR");
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

    /**
     * GET  /ref-baremes : get all the refBaremes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refBaremes in body
     */
    @GetMapping("/ref-baremes")
    @Timed
    public ResponseEntity<Set<RefBaremeDTO>> getAllRefBaremes() {

        log.debug("REST request to get a page of RefBaremes");
		try {
                   Set<RefBaremeDTO> dtos = new HashSet<>();
			       Set<RefBaremeDTO> refBaremes = refBaremeService.findAll();
                    for (RefBaremeDTO refBareme : refBaremes) {
                        if(refBareme.getAttachment() != null) {
                        	try {
                        		File file = fileStorageService.loadFile(refBareme.getAttachment().getEntityName(), refBareme.getAttachment().getName());
                                if(file != null) {
                                    refBareme.setAttachment64(Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath())));
                                    Integer a = refBareme.getAttachment().getName().lastIndexOf(".");
                					String b = refBareme.getAttachment().getName().substring(a + 1);
                					refBareme.setAttachmentName(b);
                                }	
        					} catch (Exception e) {
        						System.out.println(e);
        					}
                            
                        }
                        dtos.add(refBareme);                        
                    }
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    /**
     * GET  /ref-baremes/:id : get the "id" refBareme.
     *
     * @param id the id of the refBaremeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refBaremeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ref-baremes/{id}")
    @Timed
    public ResponseEntity<RefBaremeDTO> getRefBareme(@PathVariable Long id) {
        log.debug("REST request to get RefBareme : {}", id);
        RefBaremeDTO refBaremeDTO = refBaremeService.findOne(id);
        if(refBaremeDTO.getAttachment() != null) {
            File file = fileStorageService.loadFile(refBaremeDTO.getAttachment().getEntityName(), refBaremeDTO.getAttachment().getName());
            if(file != null) {
            	try {
					refBaremeDTO.setAttachment64(Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath())));
					Integer a = refBaremeDTO.getAttachment().getName().lastIndexOf(".");
					String b = refBaremeDTO.getAttachment().getName().substring(a + 1);
					refBaremeDTO.setAttachmentName(b);
            	} catch (IOException e) {
					e.printStackTrace();
				}
            	
            }
          }
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refBaremeDTO));
    }

    /**
     * DELETE  /ref-baremes/:id : delete the "id" refBareme.
     *
     * @param id the id of the refBaremeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ref-baremes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefBareme(@PathVariable Long id) {
        log.debug("REST request to delete RefBareme : {}", id);
        refBaremeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    /*@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }*/

    /**
     * SEARCH  /_search/ref-baremes?query=:query : search for the refBareme corresponding
     * to the query.
     *
     * @param query the query of the refBareme search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ref-baremes")
    @Timed
    public ResponseEntity<List<RefBaremeDTO>> searchRefBaremes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of RefBaremes for query {}", query);
        Page<RefBaremeDTO> page = refBaremeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ref-baremes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    
    /**
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refCompagnies in body
     */

    @GetMapping("/ref-baremes-without-pagination")
    @Timed
    public ResponseEntity<List<RefBaremeDTO>> getBaremesWithoutPagination() {
        log.debug("REST request to get a page of All baremes");
        List<RefBaremeDTO> result = refBaremeService.findBaremesWithoutPagination();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/ref-baremes-without-croquis")
    @Timed
    public ResponseEntity<Set<RefBaremeDTO>> getAllRefBaremesWithoutCroquis() {

        log.debug("REST request to get a page of RefBaremes");
		try {
			  Set<RefBaremeDTO> dtos = refBaremeService.findAll();
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

}
