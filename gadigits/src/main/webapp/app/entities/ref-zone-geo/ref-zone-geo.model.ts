import { BaseEntity } from './../../shared';

export class RefZoneGeo implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public gouvernorats?: BaseEntity[],
    ) {
    }
}
