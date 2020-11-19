package com.gaconnecte.auxilium.web.rest;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.config.ApplicationProperties;
import com.gaconnecte.auxilium.dao.KpiDao;
import com.gaconnecte.auxilium.service.AnneeService;
import com.gaconnecte.auxilium.service.AssureService;
import com.gaconnecte.auxilium.service.DevisService;
import com.gaconnecte.auxilium.service.DossierService;
import com.gaconnecte.auxilium.service.PrestationAvtService;
import com.gaconnecte.auxilium.service.PrestationPECService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing stats.
 */
@RestController
@RequestMapping("/api")
public class KpiResource {

    private final Logger log = LoggerFactory.getLogger(KpiResource.class);

    //private  DossierService dossierService;
    private  AssureService assureService;
    private  PrestationAvtService avtService;
    private  PrestationPECService pecService;
    private  DevisService devisService;
    private  AnneeService anneeService;
    
	private KpiDao kpiDao ;

	@Autowired
    public KpiResource(AnneeService anneeService, AssureService assureService, PrestationAvtService avtService,PrestationPECService pecService, DevisService devisService, KpiDao kpiDao) {
       
            this.kpiDao = kpiDao;
            this.anneeService = anneeService;
            this.assureService = assureService;
            this.avtService = avtService;
            this.pecService = pecService;
            this.devisService = devisService;
            
      
    }

    

    

    /**
     * POST  /kpi/count/dossier : count dossiers.
     *
     * @return the ResponseEntity with the dossier number
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/kpi/count/dossier/{debut}/{fin}")
    @Timed
    public ResponseEntity<Long> getDossierCount(@PathVariable("debut") String debut, @PathVariable("fin") String fin) throws URISyntaxException {
        log.debug("REST request to count dossiers : {}");
        Long result = null;
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * POST  /kpi/count/assure : count assure.
     *
     * @return the ResponseEntity with the assure number
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/kpi/count/assure/{debut}/{fin}")
    @Timed
    public ResponseEntity<Long> getAssureCount(@PathVariable("debut") String debut, @PathVariable("fin") String fin) throws URISyntaxException {
        log.debug("REST request to count assure : {}");
        Long result = assureService.getCountAssure(debut, fin);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * POST  /kpi/count/prestation/avt : count prestation avt.
     *
     * @return the ResponseEntity with the prestation avt number
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/kpi/count/prestation/avt/{debut}/{fin}")
    @Timed
    public ResponseEntity<Long> getPrestationAvtCount(@PathVariable("debut") String debut, @PathVariable("fin") String fin)  throws URISyntaxException {
        log.debug("REST request to count prestation avt : {}");
        Long result = avtService.getCountPrestationAvt(debut, fin);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * POST  /kpi/count/prestation/pec : count prestation pec.
     *
     * @return the ResponseEntity with the prestation pec number
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/kpi/count/prestation/pec/{debut}/{fin}")
    @Timed
    public ResponseEntity<Long> getPrestationPecCount(@PathVariable("debut") String debut, @PathVariable("fin") String fin) throws URISyntaxException {
        log.debug("REST request to count prestation pec : {}");
        Long result = pecService.getCountPrestationPec(debut, fin);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * POST  /kpi/count/quotation : count quotation.
     *
     * @return the ResponseEntity with the quotation number
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/kpi/count/quotation/{debut}/{fin}")
    @Timed
    public ResponseEntity<Long> getQuotationCount(@PathVariable("debut") String debut, @PathVariable("fin") String fin) throws URISyntaxException {
        log.debug("REST request to count quotation : {}");
        Long result = devisService.getCountQuotation(debut, fin);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    @GetMapping(value="/kpi/getAnnee")
	public List<Integer> getAnnee() throws URISyntaxException
	{
        log.debug("REST request to get Annee");
       	return anneeService.getAnnee();
    }
    
   


    /**
     * POST  /kpi/count/quotation : count quotation.
     *
     * @return the ResponseEntity with the quotation number
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping(value="/kpi/allPec/{debut}/{fin}")
    public String[] getAllDpec(@PathVariable("debut") String debut, @PathVariable("fin") String fin) throws SQLException {
        log.debug("REST request to get all dpec : {}");
        String[] result = new String[100];
        result = pecService.getDepcAll(debut, fin);
        return result;
    }


}
