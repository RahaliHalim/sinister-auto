import { BaseEntity } from './../../shared';
import { RefModeGestion } from './../ref-mode-gestion/ref-mode-gestion.model';
import { VehicleBrand } from './../vehicle-brand/vehicle-brand.model';
import { VisAVis } from './../vis-a-vis/vis-a-vis.model';
import { Governorate } from '../governorate/governorate.model';
import { Client } from '../client';
import { Partner } from '../partner';
export class GarantieImplique implements BaseEntity {
    constructor(
        public id?: number,
        public reparateurId?: number,
        public refModeGestions?: RefModeGestion[],
        public partnerId?: number,
        public partnerName?: string,
        public listRefCompagnie?: string,
        public listRefModeGestion?: string,
    ) {
        this.listRefCompagnie='';
        this.listRefModeGestion='';
        this.refModeGestions = [];
    }
}

export class Orientation implements BaseEntity {
    constructor(
        public id?: number,
        public reparateurId?: number,
        public remiseMarque?: number,
        public refMarques?: VehicleBrand[], 
        public refAgeVehiculeId?: number,
        public refAgeVehiculeValeur?: string,
        public listMarque?: string,
    ) {
        this.refMarques=[];
    }
}
export class Specialite implements BaseEntity {
    constructor(
        public id?: number,
        public reparateurId?: number,
        public marqueType?: number,
        public refMarques?:VehicleBrand[]
    ) {
        this.refMarques=[];
    }
}
export class RefAgeVehicule implements BaseEntity {
    constructor(
        public id?: number,
        public valeur?: string
    ) {
    }
}

export class Reparateur implements BaseEntity {
    constructor(
        public id?: number,
        public raisonSociale?: string,
        public registreCommerce?: String,
        public matriculeFiscale?: String,
        public nomPerVisVis?: String,
        public prenomPerVisVis?: String,
        public telPerVisVis?: String,
        public faxPerVisVis?: String,
        public emailPerVisVis?: String,
        public isConventionne?: Boolean,
        public isCng?: Boolean,
        public isMultiMarque?: Boolean,
        public isGaEstimate?: Boolean,
        public dateEffetConvention?: any,
        public dateFinConvention?: any,
        public isFour?: Boolean,
        public isMarbre?: Boolean,
        public isActive?: Boolean,
        public isBloque?: Boolean,
        public dateDebutBlocage?: any,
        public dateFinBlocage?: any,
        public reglementId?: number,
        public gouvernoratId?: number,
        public montantIP?: number,
        public solvant?: number,
        public hydro?: number,
        public tauxHoraireMOPeintur?: number,
        public tauxHoraireMORemplacement?: number,
        public tauxHoraireMOReparation?: number,
        public tauxHorairesReparationHauteTechnicite?: number,
        public montagePareBrise?: number,
        public montagePareBriseAvecJoint?: number,
        public montageVitreDePorte?: number,
        public montageVoletDairAvecColleOuJoint?: number,
        public tauxHoraireMOPeinturRestourne?: number,
        public tauxHoraireMORemplacementRestourne?: number,
        public tauxHorairesReparationHauteTechniciteRestourne?: number,
        public montagePareBriseRestourne?: number,
        public montagePareBriseAvecJointRestourne?: number,
        public montageVitreDePorteRestourne?: number,
        public montageVoletDairAvecColleOuJointRestourne?: number,
        public tauxHoraireMOReparationRestourne?: number,
        public montagePareBriseAvecColle?: number,
        public montagePareBriseAvecColleRestourne ?: number,
        public petiteFourniture?: number,
        public montageLunetteAriereAvecColle?: number,
        public restourneMontageLunetteAriereAvecColle?: number,
        public montageLunetteAriereAvecjoint?: number,
        public restourneMontageLunetteAriereAvecjoint ?: number,
        public villeId?: number,
        public libelleGouvernorat?: String,
        public villeLibelle?: String,
        public login?: String,
        public pwd?: String,
        public rib?: String,
        public notation?: String,
        public observation?: String,
        public isagentOfficiel?: Boolean,
        public capacite?: number,
        public adresse?: string,
        public garantieImpliques?: GarantieImplique[],
        public orientations?: Orientation[],
        public specialitePrincipales?: VehicleBrand[],
        public specialiteSecondaires?: VehicleBrand[],
        public visAViss?: VisAVis[],
        public gouvernorat?: Governorate,
        public info?: string,
        public raisonBloquageId?: number,
        public raisonBloquageLabel?: String,
    ) {
        this.isBloque = false;
        this.isCng = false;
        this.isMarbre = false;
        this.isConventionne = false;
        this.isGaEstimate = false;
        this.isActive = true;
        this.isFour = false;
        this.isagentOfficiel = false;
        this.specialitePrincipales=[];
        this.specialiteSecondaires=[];
        this.garantieImpliques=[];
        this.orientations=[];
        this.visAViss=[];
        this.info = " ";
    }
}
