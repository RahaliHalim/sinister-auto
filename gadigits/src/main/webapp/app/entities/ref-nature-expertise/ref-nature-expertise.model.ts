import { BaseEntity } from './../../shared';

export class RefNatureExpertise implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public isActive?: boolean,
    ) {
        this.isActive = false;
    }
}
