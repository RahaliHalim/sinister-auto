package com.gaconnecte.auxilium.service.dto;

import java.util.LinkedList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
public class ViewContratDT {
		private int draw = 1;
	    private Long recordsTotal;
	    private Long recordsFiltered;
	    private List<ViewContratDTO> data = new LinkedList<>();


	public List<ViewContratDTO> getData() {
		return data;
	}

	public void setData(List<ViewContratDTO> data) {
		this.data = data;
	}

	



	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	
	@Override
	public String toString() {
		return "ViewContratDT [draw=" + draw + ", recordsTotal=" + recordsTotal + ", recordsFiltered=" + recordsFiltered
				+ ", data=" + data + "]";
	}
}
