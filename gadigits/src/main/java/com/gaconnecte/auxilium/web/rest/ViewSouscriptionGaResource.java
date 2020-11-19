package com.gaconnecte.auxilium.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.service.LoggerService;
import com.gaconnecte.auxilium.service.ViewSouscriptionGaService;
import com.gaconnecte.auxilium.service.dto.DatatablesRequest;
import com.gaconnecte.auxilium.service.dto.SearchDTO;
import com.gaconnecte.auxilium.service.dto.ViewSouscriptionGaDT;
import com.gaconnecte.auxilium.service.dto.ViewSouscriptionGaDTO;
import com.gaconnecte.auxilium.service.util.ExcelUtil;

@RestController
@RequestMapping("/api/view")
public class ViewSouscriptionGaResource {
	private final Logger log = LoggerFactory.getLogger(ViewSouscriptionGaResource.class);
	private static final String ENTITY_NAME = "viewSoucriptionGa";

	private final ViewSouscriptionGaService viewSouscriptionGaService;

	@Autowired
	private LoggerService loggerService;

	public ViewSouscriptionGaResource ( ViewSouscriptionGaService viewSouscriptionGaService) {

		this.viewSouscriptionGaService =  viewSouscriptionGaService;

	}

	@GetMapping("/souscriptionGa")
	@Timed
	public List<ViewSouscriptionGaDTO> getAllSouscriptionGa() {
		return viewSouscriptionGaService.findAll();
	}


	@PostMapping("/souscriptionGa/page")
	@Timed
	public ResponseEntity<ViewSouscriptionGaDT> getAllSouscription(@RequestBody DatatablesRequest datatablesRequest) {
		ViewSouscriptionGaDT dt = new ViewSouscriptionGaDT();
		dt.setRecordsFiltered(viewSouscriptionGaService.getCountsWithFiltter(datatablesRequest.getSearchValue()));
		dt.setRecordsTotal(viewSouscriptionGaService.getCount());


		try {
			final Integer page = new Double(Math.ceil(datatablesRequest.getStart() / datatablesRequest.getLength())).intValue();
			final PageRequest pr = new PageRequest(page, datatablesRequest.getLength());
			List<ViewSouscriptionGaDTO> dtos = viewSouscriptionGaService.findAll(pr).getContent();
			if(CollectionUtils.isNotEmpty(dtos)) {
				dt.setData(dtos);
				return ResponseEntity.ok().body(dt);
			} else {
				return ResponseEntity.ok().body(dt);
			}

		} catch (Exception e) {
			loggerService.log(e, this.getClass().getName() + "." + e.getStackTrace()[0].getMethodName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

    @GetMapping(value = "/souscriptionGa/export/excel/{search}")
    @Timed
    public void exportPoliciesToExcel(@PathVariable String search, HttpServletResponse response) {
        log.debug("REST request to export all ViewPolicies ca into excel {}", search);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        XSSFWorkbook workbook = null;
        if(search.equals("-1")) search = null;
        List<ViewSouscriptionGaDTO> dtos = viewSouscriptionGaService.findAll();
        try {
            /* Logic to Export Excel */
            LocalDateTime now = LocalDateTime.now();
            String fileName = "CaSouscriptionGa" + now.format(formatter) + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getSouscriptionsGaExcelExport(dtos);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            out.flush();          
        } catch (Exception ecx) {
        	log.error("Error Occurred while exporting to XLS ", ecx);
        } finally {
            if (null != workbook) {
                try {
                    log.info("_____________________________________________________________");
                    workbook.close();
                } catch (IOException eio) {
                    log.error("Error Occurred while exporting to XLS ", eio);
                }
            }
        }

    }    





	@PostMapping("/souscriptionGa")
	@Timed
	public List<ViewSouscriptionGaDTO>findSouscriptionServices(@RequestBody SearchDTO searchDTO) {
		return viewSouscriptionGaService.Search(searchDTO);

	} 



}
