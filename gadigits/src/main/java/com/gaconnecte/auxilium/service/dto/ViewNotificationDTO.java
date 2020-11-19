package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

public class ViewNotificationDTO implements Serializable {
	 
	    private Long id;

	    private String translationCode;
	    
	    private String type;

	    private String settings;
	    
	    private Boolean read;
	    
	    private Long destination;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTranslationCode() {
			return translationCode;
		}

		public void setTranslationCode(String translationCode) {
			this.translationCode = translationCode;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getSettings() {
			return settings;
		}

		public void setSettings(String settings) {
			this.settings = settings;
		}

		public Boolean getRead() {
			return read;
		}

		public void setRead(Boolean read) {
			this.read = read;
		}

		public Long getDestination() {
			return destination;
		}

		public void setDestination(Long destination) {
			this.destination = destination;
		}
	    
}
