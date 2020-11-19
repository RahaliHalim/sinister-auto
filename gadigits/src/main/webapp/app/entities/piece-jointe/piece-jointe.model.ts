import { BaseEntity } from './../../shared';

export class PieceJointe implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public chemin?: string,
        public isOriginale?: boolean,
        public dateImport?: any,
        public typeId?: number,
        public typeLibelle?: string,
        public note?: string,
        public userId?: number,
        public userLogin?: string,
        public prestationId?: number,
        public signedAgreementUrl?: any,
        public prestationReference?: string,
        public file?: File,
    ) {
        this.isOriginale = false;
    }
}
