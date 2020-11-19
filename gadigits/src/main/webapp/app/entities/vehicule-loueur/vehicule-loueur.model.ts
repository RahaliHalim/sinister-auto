import { BaseEntity } from './../../shared';

export class VehiculeLoueur implements BaseEntity {
    constructor(
        public id?: number,
        public immatriculation?: string,
        public totalPrix?: number,
        public prixJour?: number,
        public modeleId ?: number,
        public modeleLabel?: string,
        public marqueId ?: number,
        public marqueLabel ?: string,
        public loueurId ?: number,        
        public loueurLabel?: string,



    ) {
    }
}
