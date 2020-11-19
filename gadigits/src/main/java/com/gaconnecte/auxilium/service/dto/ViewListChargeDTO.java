package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

public class ViewListChargeDTO  implements Serializable {

	
	
	    private Long id;
	    
	    private String nomCharge;
		private String prenomCharge;

	    
	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNomCharge() {
			return nomCharge;
		}

		public void setNomCharge(String nomCharge) {
			this.nomCharge = nomCharge;
		}

		public String getPrenomCharge() {
			return prenomCharge;
		}

		public void setPrenomCharge(String prenomCharge) {
			this.prenomCharge = prenomCharge;
		}


		@Override
		public String toString() {
			return "ViewListCharge [id=" + id + ", nomCharge=" + nomCharge + ", prenomCharge=" + prenomCharge + "]";
		}
		
		
		
}
