
package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.app.Attachment;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.service.UploadService;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.UploadDTO;
import com.gaconnecte.auxilium.service.impl.FileStorageService;

import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;


/**
 * REST controller for managing Tiers.
 */
@RestController
@RequestMapping("/api")
public class UploadResource {

    private final Logger log = LoggerFactory.getLogger(TiersResource.class);

    private static final String ENTITY_NAME = "upload";
    private final AttachmentRepository attachmentRepository;
    private final FileStorageService fileStorageService;
    private final UploadService uploadService;

    public UploadResource (UploadService uploadService, AttachmentRepository attachmentRepository, FileStorageService fileStorageService) {
        this.uploadService = uploadService;
        this.attachmentRepository = attachmentRepository;
        this.fileStorageService = fileStorageService;
    }
    /**
     * POST  /tiers : Create a new tiers.
     *
     * @param tiersDTO the tiersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tiersDTO, or with status 400 (Bad Request) if the tiers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    @PostMapping(value = "/upload", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<UploadDTO> createUpload(@RequestPart(name = "croquis", required = false) MultipartFile casupload, @RequestPart(name = "refupload") UploadDTO uploadDTO) throws URISyntaxException {
        log.debug("REST request to save upload : {}", uploadDTO);
        UploadDTO result = uploadService.save(casupload, uploadDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
    

    /**
     * GET  /ref-baremes/:id : get the "id" refBareme.
     *
     * @param id the id of the refBaremeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refBaremeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/upload/{id}")
    @Timed
    public ResponseEntity<UploadDTO> getUpload(@PathVariable Long id) {
        log.debug("REST request to get UploadDAO : {}", id);
        UploadDTO uploadDTO = uploadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uploadDTO));
    }
    
    /**
     * GET  /upload : get all the upload.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tiers in body
     */
    @GetMapping("/upload")
    @Timed
    public List<UploadDTO> getAllUploadDTO() {
        log.debug("REST request to get a page of UploadDTO");
        List<UploadDTO> uploads = uploadService.findAll();
        for(UploadDTO upl : uploads) {
        	Attachment attachment = attachmentRepository.findAttachmentsByEntityIdAndEntityName("refUpload", upl.getId());
        	upl.setOriginalName(attachment.getOriginalName());
        	if(attachment != null) {
        		upl.setOriginalName(attachment.getOriginalName());
        	}
        }
        return uploads;
    }
    
    @GetMapping(value = "/upload/xls/{id}")
    @Timed
    public void getRefXls(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ref xls : {}", id);
        Attachment attachment = attachmentRepository.findAttachmentsByEntityIdAndEntityName("refUpload", id);
        try {
            String fileName = "ref" + System.currentTimeMillis() + ".xlsx";

            OutputStream out = response.getOutputStream();
            File file = fileStorageService.loadFile("refUpload", attachment.getName());
            if (file != null) {
                IOUtils.copy(new FileInputStream(file), out);
            }
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            out.flush();
        } catch (Exception ecx) {
            log.error("Error Occurred while exporting to XLS ", ecx);
        }
    }
    
    
    }