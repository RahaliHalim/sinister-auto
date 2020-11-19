import { VehiculeLoueur } from '../vehicule-loueur';
import { VisAVis } from '../vis-a-vis/vis-a-vis.model';
import { BaseEntity } from './../../shared';

export class Loueur implements BaseEntity {
    constructor(
        public id?: number,
        public code?: number,
        public raisonSociale?: string,
        public matriculeFiscale?: string,
        public registreCommerce?: string,
        public conventionne?: boolean,
        public dateEffetConvention?: any,
        public dateFinConvention?: any,
        public nbrVehicules?: number,
        public address?: string,
        public blocage?: boolean,
        public rib?: string,
        public reglementId?: number,
        public governorateId?: number,
        public governorateLabel?: string,
        public delegationId?: number,
        public delegationLabel?: string,
        public visAViss?: VisAVis[],
        public vehicules?: VehiculeLoueur[],
        public telephone?: string,
        public reclamation?: string,
        public motifId?: number,
    


    ) {
        this.conventionne = false;
        this.blocage = false;
        this.visAViss=[];
        this.vehicules=[];


    }
}
