import { BaseEntity } from './../../shared';

export class SysGouvernorat implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public latitude?: number,
        public longitude?: number,
        public codeIso?: string,
        public zones?: BaseEntity[],
    ) {
    }
}
