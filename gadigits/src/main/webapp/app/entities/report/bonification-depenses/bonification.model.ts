import { BaseEntity } from '../../../shared';
export class Bonification implements BaseEntity {
    constructor(
        public id?: number,
        public compagnieId?: number,
        public compagnie?: string,
        public numContrat?: string,
        public zoneId?: number,
        public zone?: string,
        public packId?: number,
        public pack?: string,
        public referenceGa?: string,
        public reparateurId?: number,
        public reparateurName?: string,
        public reparateurCode?: string,
        public idZoneReparateur?: number,
        public zoneReparateur?: string,
        public dateBonSortie?: Date,
        public moisBonSortie?: string,
        public mdIp?: number,
        public piece?: number,
        public partGa?: number,
        public partCie?: number,
        public creationDate?: Date,
        public serviceId?: number,
        public service?: string,
        public chargeId?: number,
        public charge?: string,
        public expertId?: number,
        public expert?: string,
        public baseCalculModIp?: number,
        public baseCalculPiece?: number,
        public dateOuverture?: Date,
        public marque?: string,
        public marqueCode?: string,


        

    ) {
    }
}
