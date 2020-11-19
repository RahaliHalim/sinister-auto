package com.gaconnecte.auxilium.service.dto;

import java.util.LinkedList;
import java.util.List;

public class ReportFrequencyRateDT {
	 private int draw = 1;
	    private Long recordsTotal;
	    private Long recordsFiltered;
	    private List<ReportFrequencyRateDTO> data = new LinkedList<>();
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
		public List<ReportFrequencyRateDTO> getData() {
			return data;
		}
		public void setData(List<ReportFrequencyRateDTO> data) {
			this.data = data;
		}
}
