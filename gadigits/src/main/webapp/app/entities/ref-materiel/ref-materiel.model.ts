import { BaseEntity } from './../../shared';

export class RefMateriel implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public description?: string,
        public obligatoireCng?: boolean,
        public reparateurs?: BaseEntity[],
    ) {
        this.obligatoireCng = false;
    }
}
