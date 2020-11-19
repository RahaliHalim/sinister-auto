import { BaseEntity } from '../../shared';

export class RefTarif implements BaseEntity {
    constructor(
        public id?: number,
        public libelleTarif?: string,
        public lineId?: number,
        public montant?: number
    ) {
    }
}
