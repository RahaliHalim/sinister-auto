package com.gaconnecte.auxilium.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.PartnerService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.service.dto.AgencyDTO;
import com.gaconnecte.auxilium.service.dto.AttachmentDTO;
import com.gaconnecte.auxilium.service.dto.PartnerDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.dto.UserPartnerModeDTO;
import com.gaconnecte.auxilium.repository.AttachmentRepository;
import com.gaconnecte.auxilium.service.impl.FileStorageService;
import com.gaconnecte.auxilium.domain.app.Attachment;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import com.gaconnecte.auxilium.domain.RefBareme;
import com.gaconnecte.auxilium.repository.RefBaremeRepository;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;

import java.util.List;
import java.util.Map;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.json.JSONException;
import org.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Comparator;

/**
 * REST controller for managing Partner.
 */
@RestController
@RequestMapping("/api")
public class PartnerResource {

    private final Logger log = LoggerFactory.getLogger(PartnerResource.class);

    private static final String ENTITY_NAME = "partner";

    private final PartnerService partnerService;
    private final UserExtraService userExtraService;
    private final UserService userService;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private RefBaremeRepository refBaremeRepository;

    public PartnerResource(PartnerService partnerService, UserExtraService userExtraService, UserService userService) {
        this.partnerService = partnerService;
        this.userExtraService = userExtraService;
        this.userService = userService;
    }

    /**
     * POST /partners : Create a new partner.
     *
     * @param partnerDTO the partnerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         partnerDTO, or with status 400 (Bad Request) if the partner has
     *         already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/partners")
    @Timed
    public ResponseEntity<PartnerDTO> createPartner(@RequestBody PartnerDTO partnerDTO) throws URISyntaxException {
        log.debug("REST request to save Partner : {}", partnerDTO);
        if (partnerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(
                    HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new partner cannot already have an ID"))
                    .body(null);
        }
        PartnerDTO result = partnerService.save(partnerDTO);
        if (result != null) {
            historyService.historysave("Partner", result.getId(), null, result, 0, 1, "Création");
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * PUT /partners : Updates an existing partner.
     *
     * @param partnerDTO the partnerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         partnerDTO, or with status 400 (Bad Request) if the partnerDTO is not
     *         valid, or with status 500 (Internal Server Error) if the partnerDTO
     *         couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/partners")
    @Timed
    public ResponseEntity<PartnerDTO> updatePartner(@RequestBody PartnerDTO partnerDTO) throws URISyntaxException {
        log.debug("REST request to update Partner : {}", partnerDTO);
        if (partnerDTO.getId() == null) {
            return createPartner(partnerDTO);
        }
        PartnerDTO oldPartner = partnerService.findOne(partnerDTO.getId());
        PartnerDTO result = partnerService.save(partnerDTO);
        historyService.historysave("Partner", result.getId(), oldPartner, result, 1, 1, "Modification");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PostMapping(value = "/concessionnaire/attachments/{id}/{label}/{nomImage}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createLogoConcessionnaire(@PathVariable Long id, @PathVariable String label,
            @PathVariable String nomImage,
            @RequestPart(name = "concessionnaire", required = true) MultipartFile LogoFile) throws URISyntaxException {
        log.debug("REST request to save files partner ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = partnerService.saveAttachmentLogoConcessionnaire(LogoFile, id, label, nomImage);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PutMapping(value = "/concessionnaire/attachments/{id}/{label}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> updateLogoConcessionnaire(@PathVariable Long id, @PathVariable String label,
            @RequestPart(name = "concessionnaire", required = false) MultipartFile LogoFile) throws URISyntaxException {
        log.debug("REST request to update files partner ------------------------------------: {}", id);
        AttachmentDTO result = new AttachmentDTO();
        result = partnerService.updateAttachmentLogoConcessionnaire(LogoFile, id, label);
        // historyService.historysave(label, result.getId(), "MISE A JOUR");
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PostMapping(value = "/partners/attachments/{id}/{label}/{nomImage}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> createLogoCompany(@PathVariable Long id, @PathVariable String label,
            @PathVariable String nomImage, @RequestPart(name = "company", required = true) MultipartFile LogoFile)
            throws URISyntaxException {
        log.debug("REST request to save files partner ------------------------------------: {}", id);

        AttachmentDTO result = new AttachmentDTO();
        result = partnerService.saveAttachmentLogoCompany(LogoFile, id, label, nomImage);
        // historyService.historysave(label, result.getId(), "CREATION");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @PutMapping(value = "/partners/attachments/{id}/{label}", consumes = { "multipart/form-data" })
    @Timed
    public ResponseEntity<AttachmentDTO> updateLogoCompany(@PathVariable Long id, @PathVariable String label,
            @RequestPart(name = "company", required = false) MultipartFile LogoFile) throws URISyntaxException {
        log.debug("REST request to update files partner ------------------------------------: {}", id);
        AttachmentDTO result = new AttachmentDTO();
        result = partnerService.updateAttachmentLogoCompany(LogoFile, id, label);
        // historyService.historysave(label, result.getId(), "MISE A JOUR");
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @GetMapping("partners/attachments/{id}")
    @Timed
    public HttpEntity<List<AttachmentDTO>> getAttachments(@ApiParam Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get Attachments for partner pec : {}", id);
        Page<AttachmentDTO> attachmentDTO = partnerService.findAttachments(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(attachmentDTO, "/api/attachement");
        return new ResponseEntity<>(attachmentDTO.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /partners : get all the partners.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partners in
     *         body
     */
    @GetMapping("/partners")
    @Timed
    public List<PartnerDTO> getAllPartners() {
        log.debug("REST request to get all Partners");
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        UserExtraDTO userExtraDTO = userExtraService.findOne(user.getId());
        List<PartnerDTO> dtos = new LinkedList<>();
        Map<Long, PartnerDTO> map = new HashMap<>();
        if (userExtraDTO != null && (new Long(25l).equals(userExtraDTO.getProfileId())
                || new Long(26l).equals(userExtraDTO.getProfileId()))) { // company
            PartnerDTO p = partnerService.findOne(userExtraDTO.getPersonId());
            dtos.add(p);
            return dtos;
        }
        if (userExtraDTO != null && (new Long(23l).equals(userExtraDTO.getProfileId())
                || new Long(24l).equals(userExtraDTO.getProfileId()) || new Long(6l).equals(userExtraDTO.getProfileId())
                || new Long(7l).equals(userExtraDTO.getProfileId())
                || new Long(8l).equals(userExtraDTO.getProfileId()))) { // agent
            Set<UserPartnerModeDTO> partnerModes = userExtraDTO.getUserPartnerModes();
            for (UserPartnerModeDTO partnerMode : partnerModes) {
                PartnerDTO p = partnerService.findOne(partnerMode.getPartnerId());
                if (!map.containsKey(p.getId())) {
                    map.put(p.getId(), p);
                }
            }
            if (CollectionUtils.isNotEmpty(map.values())) {
                dtos.addAll(map.values());
            }
            return dtos;
        }

        return partnerService.findAll();
    }

    /**
     * GET /partners/companies : get all the companies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partners in
     *         body
     */
    @GetMapping("/partners/companies")
    @Timed
    public List<PartnerDTO> getAllCompanies() {
        log.debug("REST request to get all companies");
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userService.findOneUserByLogin(login);
        UserExtraDTO userExtraDTO = userExtraService.findOne(user.getId());
        List<PartnerDTO> dtos = new LinkedList<>();
        Map<Long, PartnerDTO> map = new HashMap<>();
        if (userExtraDTO != null && new Long(25l).equals(userExtraDTO.getProfileId())) { // company
            PartnerDTO p = partnerService.findOne(userExtraDTO.getPersonId());
            dtos.add(p);
            return dtos;
        }
        if (userExtraDTO != null && (new Long(23l).equals(userExtraDTO.getProfileId())
                || new Long(24l).equals(userExtraDTO.getProfileId()) || new Long(6l).equals(userExtraDTO.getProfileId())
                || new Long(7l).equals(userExtraDTO.getProfileId())
                || new Long(8l).equals(userExtraDTO.getProfileId()))) { // agent
            Set<UserPartnerModeDTO> partnerModes = userExtraDTO.getUserPartnerModes();
            for (UserPartnerModeDTO partnerMode : partnerModes) {
                PartnerDTO p = partnerService.findOne(partnerMode.getPartnerId());
                if (!map.containsKey(p.getId())) {
                    map.put(p.getId(), p);
                }
            }
            if (CollectionUtils.isNotEmpty(map.values())) {
                dtos.addAll(map.values());
            }
            return dtos;
        }

        return partnerService.findAll();

    }

    @GetMapping("/partners/companies-without-user")
    @Timed
    public List<PartnerDTO> getAllCompaniesWithoutUser() {
        log.debug("REST request to get all companies");
        return partnerService.findAllCompanies();

    }

    @GetMapping("/allpartners")
    @Timed
    public List<PartnerDTO> getAll() {
        return partnerService.findAll();
    }

    /**
     * GET /partners/dealers : get all the dealers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partners in
     *         body
     */
    @GetMapping("/partners/dealers")
    @Timed
    public List<PartnerDTO> getAllDealers() {
        log.debug("REST request to get all dealers");
        return partnerService.findAllDealers();
    }

    @PostMapping("/partners/CompbyNameReg")
    @Timed
    public ResponseEntity<PartnerDTO> getCompanyByNameReg(@RequestBody PartnerDTO pname) {
        log.debug("REST request to get partner : {}", pname);
        String tradeRegister = pname.getTradeRegister();
        String companyName = pname.getCompanyName();
        PartnerDTO partnerDTO = partnerService.getCompanyByNameReg(companyName, tradeRegister, 1);
        if (partnerDTO == null) {
            partnerDTO = new PartnerDTO();
        }
        return ResponseEntity.ok().body(partnerDTO);
    }

    @PostMapping("/partners/DealerbyNameReg")
    @Timed
    public ResponseEntity<PartnerDTO> getDealerByNameReg(@RequestBody PartnerDTO pname) {
        log.debug("REST request to get partner : {}", pname);
        String tradeRegister = pname.getTradeRegister();
        String companyName = pname.getCompanyName();
        PartnerDTO partnerDTO = partnerService.getDealerByNameReg(companyName, tradeRegister, 2);
        if (partnerDTO == null) {
            partnerDTO = new PartnerDTO();
        }
        return ResponseEntity.ok().body(partnerDTO);

    }

    /**
     * GET /partners/:id : get the "id" partner.
     *
     * @param id the id of the partnerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partnerDTO,
     *         or with status 404 (Not Found)
     */
    @GetMapping("/partners/{id}")
    @Timed
    public ResponseEntity<PartnerDTO> getPartner(@PathVariable Long id) {
        log.debug("REST request to get Partner : {}", id);
        PartnerDTO partnerDTO = partnerService.findOne(id);
        // partnerService.convertFileToBase64(partnerDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(partnerDTO));
    }

    @GetMapping(value = "/partners/pieces-jointes-file/{id}/{entityName}/{label}")
    @Timed
    public void getPiecesJointesFiles(@PathVariable Long id, @PathVariable String entityName,
            @PathVariable String label, HttpServletResponse response) {
        log.debug("REST request to get ref xls : {}", id);
        Attachment attachment = new Attachment();
        if ((new String("refBareme").equals(entityName))) {
            RefBareme refBareme = refBaremeRepository.findOne(id);
            attachment = refBareme.getAttachment();
        } else {
            Set<Attachment> attachments = attachmentRepository.findAttachmentsByEntityAndEntityNameAndLabel(entityName,
                    id, label);
            Comparator<Attachment> comparator = Comparator.comparing(Attachment::getId);
            attachment = attachments.stream().max(comparator).get();

        }
        Integer a = attachment.getName().lastIndexOf(".");
        String b = attachment.getName().substring(a + 1);
        File file = fileStorageService.loadFile(entityName, attachment.getName());
        String fileName = "ref" + System.currentTimeMillis() + "." + b;
        try (InputStream fileInputStream = new FileInputStream(file);
                OutputStream output = response.getOutputStream();) {

            response.reset();
            if ((new String("pdf")).equals(b)) {
                response.setContentType("application/pdf;charset=UTF-8");
            } else if ((new String("jpg")).equals(b)) {
                response.setContentType("image/jpeg;charset=UTF-8");
            } else {
                response.setContentType("image/" + b + ";charset=UTF-8");
            }
            response.setContentLength((int) (file.length()));
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            IOUtils.copy(fileInputStream, output);
            output.flush();
        } catch (Exception ecx) {
            log.error("Error Occurred while exporting to XLS ", ecx);
        }
    }

    /**
     * DELETE /partners/:id : delete the "id" partner.
     *
     * @param id the id of the partnerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/partners/{id}")
    @Timed
    public void deletePartner(@PathVariable Long id) {
        log.debug("REST request to delete Partner : {}", id);
        partnerService.delete(id);
    }

    /**
     * SEARCH /_search/partners?query=:query : search for the partner corresponding
     * to the query.
     *
     * @param query the query of the partner search
     * @return the result of the search
     */
    @GetMapping("/_search/partners")
    @Timed
    public List<PartnerDTO> searchPartners(@RequestParam String query) {
        log.debug("REST request to search Partners for query {}", query);
        return partnerService.search(query);
    }

}
