import { BaseEntity } from './../../shared';

export class SysVille implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public latitude?: number,
        public longitude?: number,
        public gouvernoratId?: number,
        public gouvernoratLibelle?: string,
    ) {
    }
}
