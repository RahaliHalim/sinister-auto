import { BaseEntity } from './../../shared';

export class RefEtatDossier implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
    ) {
    }
}
