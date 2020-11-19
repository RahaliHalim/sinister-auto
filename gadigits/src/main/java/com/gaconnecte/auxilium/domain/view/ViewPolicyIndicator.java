package com.gaconnecte.auxilium.domain.view;


import java.io.Serializable;

/**
 * A ViewPolicyIndicator.
 */
public class ViewPolicyIndicator implements Serializable {

    private static final long serialVersionUID = 1L;

    private String agencyName;

    private String usageLabel;

    private Long packNumber;

    private String zoneLabel;

	public ViewPolicyIndicator(String agencyName, String usageLabel, Long packNumber, String zoneLabel) {
		this.agencyName = agencyName;
		this.usageLabel = usageLabel;
		this.packNumber = packNumber;
		this.zoneLabel = zoneLabel;
	}

	public ViewPolicyIndicator() {
	}
	
	/**
	 * @return the agencyName
	 */
	public String getAgencyName() {
		return agencyName;
	}

	/**
	 * @param agencyName the agencyName to set
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	/**
	 * @return the usageLabel
	 */
	public String getUsageLabel() {
		return usageLabel;
	}

	/**
	 * @param usageLabel the usageLabel to set
	 */
	public void setUsageLabel(String usageLabel) {
		this.usageLabel = usageLabel;
	}

	/**
	 * @return the packNumber
	 */
	public Long getPackNumber() {
		return packNumber;
	}

	/**
	 * @param packNumber the packNumber to set
	 */
	public void setPackNumber(Long packNumber) {
		this.packNumber = packNumber;
	}

	/**
	 * @return the zoneLabel
	 */
	public String getZoneLabel() {
		return zoneLabel;
	}

	/**
	 * @param zoneLabel the zoneLabel to set
	 */
	public void setZoneLabel(String zoneLabel) {
		this.zoneLabel = zoneLabel;
	}

}
