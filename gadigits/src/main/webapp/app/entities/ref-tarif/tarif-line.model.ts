import { BaseEntity } from '../../shared';

export class TarifLine implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
       
    ) {
    }
}
