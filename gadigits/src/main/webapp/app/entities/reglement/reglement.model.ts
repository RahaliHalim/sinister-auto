import { BaseEntity } from './../../shared';

export class Reglement implements BaseEntity {
    constructor(
        public id?: number,
        public echeanceJours?: number,
        public reparateurs?: BaseEntity[],
        public modeReglementId?: number,
    ) {
    }
}
