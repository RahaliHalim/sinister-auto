package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Vehicle entity.
 */
public class VehicleDTO implements Serializable {

    private Long id;

    private String registrationNumber;

    private String chassisNumber;

    private LocalDate firstEntryIntoService;

    private Integer fiscalPower;

    private Long brandId;

    private String brandLabel;

    private Long modelId;

    private String modelLabel;

    private Long energyId;

    private String energyLabel;

    private Long usageId;

    private String usageLabel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public LocalDate getFirstEntryIntoService() {
        return firstEntryIntoService;
    }

    public void setFirstEntryIntoService(LocalDate firstEntryIntoService) {
        this.firstEntryIntoService = firstEntryIntoService;
    }

    public Integer getFiscalPower() {
        return fiscalPower;
    }

    public void setFiscalPower(Integer fiscalPower) {
        this.fiscalPower = fiscalPower;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long vehicleBrandId) {
        this.brandId = vehicleBrandId;
    }

    public String getBrandLabel() {
        return brandLabel;
    }

    public void setBrandLabel(String vehicleBrandLabel) {
        this.brandLabel = vehicleBrandLabel;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long vehicleBrandModelId) {
        this.modelId = vehicleBrandModelId;
    }

    public String getModelLabel() {
        return modelLabel;
    }

    public void setModelLabel(String vehicleBrandModelLabel) {
        this.modelLabel = vehicleBrandModelLabel;
    }

    public Long getEnergyId() {
        return energyId;
    }

    public void setEnergyId(Long vehicleEnergyId) {
        this.energyId = vehicleEnergyId;
    }

    public String getEnergyLabel() {
        return energyLabel;
    }

    public void setEnergyLabel(String vehicleEnergyLabel) {
        this.energyLabel = vehicleEnergyLabel;
    }

    public Long getUsageId() {
        return usageId;
    }

    public void setUsageId(Long vehicleUsageId) {
        this.usageId = vehicleUsageId;
    }

    public String getUsageLabel() {
        return usageLabel;
    }

    public void setUsageLabel(String vehicleUsageLabel) {
        this.usageLabel = vehicleUsageLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleDTO vehicleDTO = (VehicleDTO) o;
        if(vehicleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
            "id=" + getId() +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", chassisNumber='" + getChassisNumber() + "'" +
            ", firstEntryIntoService='" + getFirstEntryIntoService() + "'" +
            ", fiscalPower='" + getFiscalPower() + "'" +
            "}";
    }
}
