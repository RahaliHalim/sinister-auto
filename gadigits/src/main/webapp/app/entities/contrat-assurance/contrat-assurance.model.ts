import { BaseEntity } from './../../shared';
import { VehicleBrandModel } from '../vehicle-brand-model/vehicle-brand-model.model';
import { VehicleBrand } from '../vehicle-brand/vehicle-brand.model';
import { VehiculeAssure } from '../vehicule-assure/vehicule-assure.model';

export class ContratAssurance implements BaseEntity {
    constructor(
        public id?: number,
        public numeroContrat?: string,
        public debutValidite?: any,
        public deadlineDate?: any,
        public amendmentEffectiveDate?: any,
        public receiptValidityDate?: any,
        public finValidite?: any,
        public isSuspendu?: boolean,
        public isDelete?: boolean,
        public newValueVehicle?: number,
        public dcCapital?: number,
        public bgCapital?: number,
        public marketValue?: number,
        public primeAmount?: number,
        public souscripteur?: string,
        public commentaire?: string,
        public typeId?: number,
        public natureId?: number,
        public agenceId?: number,
        public agenceNom?: number,
        public nomCompagnie?: string,
        public usageId?: number,
        public vehiculeId?: number,
        public vehiculeImmatriculationVehicule?: string,
        public assureId?: number,
        public assurePrenom?: String,
        public assureRaison?: String,
        public fullName?: String,
        public fractionnementId?: number,
        public fractionnementLibelle?: String,
        public packId?: number,
        public packNom?: String,
        public garanties?: BaseEntity[],
        public villeLibelle?: String,
        public govLibelle?: String,
        public villeMoraleLibelle?: String,
        public adressePhysique?: String,
        public adresseMorale?: String,
        public marqueLibelle?: string,
        public clientId?: number,
        public isAssujettieTva?: boolean,
        public isRachat?: boolean,
        public newValueVehicleFarnchise?: number,
        public dcCapitalFranchise?: number,
        public bgCapitalFranchise?: number,
        public marketValueFranchise?: number,
        public franchiseTypeNewValue?: number,
        public franchiseTypeMarketValue?: number,
        public franchiseTypeDcCapital?: number,
        public franchiseTypeBgCapital?: number,
        public immatriculationVehicule?: string,
        public puissance?: number,
        public numeroChassis?: string,
        public datePCirculation?: any,
        public modeleId?: number,
        public marqueId?: number,
        public modeleLibelle?: string,
        public energieId?: number,
        public modele?: VehicleBrandModel,
        public marque?: VehicleBrand,
        public contratId?: number,
        public vehicules?: VehiculeAssure[],
        public isExtract?: boolean,
        public interventionNumber?: number,
        public doneInterventionNumber?: number,
        public statusId?: number,
        public statusLabel?: string,
        public receiptStatusId?: number,
        public receiptStatusLabel?: string,
        public amendmentTypeId?: number,
        public amendmentTypeLabel?: string,
        public typeLabel?: string,
        public natureLabel?: string

   
    ) {
        this.isSuspendu = false;
        this.isRachat = false;
        this.isExtract = false;
        this.isAssujettieTva = false;
        this.vehicules = [];
    }
}
