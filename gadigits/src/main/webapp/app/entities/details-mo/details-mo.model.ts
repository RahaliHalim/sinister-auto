import { BaseEntity } from './../../shared';

export class DetailsMo implements BaseEntity {
    constructor(
        public id?: number,
        public nombreHeures?: number,
        public designationId?: number,
        public designationReference?: string,
        public factureMos?: BaseEntity[],
        public devisId?: number,
        public typeInterventionId?: number,
        public typeInterventionLibelle?: string,
        public th?: number,
        public vat?: number,
        public discount?: number,
        public controlIndex?: number,
        public totalHt?: number,
        public totalTtc?: number,
        public observation?: string,
    ) {
    }
}
