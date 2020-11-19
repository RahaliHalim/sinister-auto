import { BaseEntity } from '../../shared';

export class ObservationApec implements BaseEntity {
    constructor(
        public id?: number,
        public observationAssure?: string,
        public decriptionObservationAssure?: string,
        public apecId?: number,
        public approuvDate?: any,
        public modifDate?: any,
        public validationDate?: any,
        public assureValidationDate?: any,
        public imprimDate?: any,
        public decisionApprobationCompagnie?: number,
        public decisionValidationApec?: number,
        public decisionValidationPartAssure?: number,
    ) {
    }
}
