import { BaseEntity } from './../../shared';

export class RaisonAssistance implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public label?: string,
        public active?: boolean,
        public creationDate?: any,
        public creationUserId?: number,
        public operationId?: number,
        public statusId?: number,
        public statusLabel?: string,
    ) {
        this.active = true;
    }
}
