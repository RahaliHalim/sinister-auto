import { BaseEntity } from './../../shared';

export enum TypeObservation {
    'Observation',
    'Système'
}

export class Observation implements BaseEntity {
    constructor(
        public id?: number,
        public commentaire?: string,
        public date?: any,
        public prive?: boolean,
        public type?: TypeObservation,
        public userId?: number,
        public userLogin?: string,
        public sinisterPecId?: number,
        public sinisterPrestationId?: number,
        public devisId?: number,
        public prestationId?: number,
        public firstName?: string,
        public lastName?: string,

        public prestationReference?: string,
        public deleted?: boolean,
        
    ) {
        this.prive = false;
        this.deleted = false;
    }
}
