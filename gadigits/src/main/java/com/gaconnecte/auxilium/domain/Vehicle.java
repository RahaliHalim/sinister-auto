package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "app_vehicle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "app_vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "first_entry_into_service")
    private LocalDate firstEntryIntoService;

    @Column(name = "fiscal_power")
    private Integer fiscalPower;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private VehicleBrand brand;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private VehicleBrandModel model;

    @ManyToOne
    @JoinColumn(name = "energy_id")
    private VehicleEnergy energy;

    @ManyToOne
    @JoinColumn(name = "usage_id")
    private VehicleUsage usage;
    
    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Vehicle registrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public Vehicle chassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
        return this;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public LocalDate getFirstEntryIntoService() {
        return firstEntryIntoService;
    }

    public Vehicle firstEntryIntoService(LocalDate firstEntryIntoService) {
        this.firstEntryIntoService = firstEntryIntoService;
        return this;
    }

    public void setFirstEntryIntoService(LocalDate firstEntryIntoService) {
        this.firstEntryIntoService = firstEntryIntoService;
    }

    public Integer getFiscalPower() {
        return fiscalPower;
    }

    public Vehicle fiscalPower(Integer fiscalPower) {
        this.fiscalPower = fiscalPower;
        return this;
    }

    public void setFiscalPower(Integer fiscalPower) {
        this.fiscalPower = fiscalPower;
    }

    public VehicleBrand getBrand() {
        return brand;
    }

    public Vehicle brand(VehicleBrand vehicleBrand) {
        this.brand = vehicleBrand;
        return this;
    }

    public void setBrand(VehicleBrand vehicleBrand) {
        this.brand = vehicleBrand;
    }

    public VehicleBrandModel getModel() {
        return model;
    }

    public Vehicle model(VehicleBrandModel vehicleBrandModel) {
        this.model = vehicleBrandModel;
        return this;
    }

    public void setModel(VehicleBrandModel vehicleBrandModel) {
        this.model = vehicleBrandModel;
    }

    public VehicleEnergy getEnergy() {
        return energy;
    }

    public Vehicle energy(VehicleEnergy vehicleEnergy) {
        this.energy = vehicleEnergy;
        return this;
    }

    public void setEnergy(VehicleEnergy vehicleEnergy) {
        this.energy = vehicleEnergy;
    }

    public VehicleUsage getUsage() {
        return usage;
    }

    public Vehicle usage(VehicleUsage vehicleUsage) {
        this.usage = vehicleUsage;
        return this;
    }

    public void setUsage(VehicleUsage vehicleUsage) {
        this.usage = vehicleUsage;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        if (vehicle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", chassisNumber='" + getChassisNumber() + "'" +
            ", firstEntryIntoService='" + getFirstEntryIntoService() + "'" +
            ", fiscalPower='" + getFiscalPower() + "'" +
            "}";
    }
}
