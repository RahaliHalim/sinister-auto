import { BaseEntity } from './../../shared';

const enum Responsible {
    'ga',
    'agent',
    'company'
}

export class RaisonPec implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public label?: string,
        public active?: boolean,
        public creationDate?: any,
        public creationUserId?: number,
        public responsible?: Responsible,
        public operationId?: number,
        public operationLabel?: number,
        public pecStatusChangeMatrixId?: number,
        public pecStatusChangeMatrixLabel?: number,
        public statusPecId?: number,
        public statusPecLabel?: string,

    ) {
        this.active = true;
    }
}
