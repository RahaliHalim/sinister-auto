import { BaseEntity } from './../../shared';
import { VehicleBrandModel } from '../vehicle-brand-model/vehicle-brand-model.model';
import { VehicleBrand } from '../vehicle-brand/vehicle-brand.model';
import { Assure } from '../assure/assure.model';

export class VehiculeAssure implements BaseEntity {
    constructor(
        public id?: number,
        public immatriculationVehicule?: string,
        public puissance?: number,
        public numeroChassis?: string,
        public datePCirculation?: any,
        public modeleId?: number,
        public marqueId?: number,
        public marqueLibelle?: string,
        public modeleLibelle?: string,
        public usageId?: number,
        public energieId?: number,
        public usageLibelle?: string,
        public energieLibelle?: string,        
        public modele?: VehicleBrandModel,
        public marque?: VehicleBrand,
        public contratId?: number,
        public partnerId?: number,
        public deleted?: boolean,
        public placesNumber?: number,
        public packId?: number,
        public packLabel?: string,
        public newValueVehicle?: number,
        public dcCapital?: number,
        public bgCapital?: number,
        public marketValue?: number,
        public newValueVehicleFarnchise?: number,
        public dcCapitalFranchise?: number,
        public bgCapitalFranchise?: number,
        public marketValueFranchise?: number,
        public franchiseTypeNewValue?: number,
        public franchiseTypeMarketValue?: number,
        public franchiseTypeDcCapital?: number,
        public franchiseTypeBgCapital?: number,
        public insuredId?: number,
        public insured?: Assure,
        public compagnyName?: string,
        public assujettieTVA?: boolean

    ) {
        this.deleted = false;
        this.insured = new Assure();
    }
}
