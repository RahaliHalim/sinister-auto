import { BaseEntity } from './../../shared';

export class RefModeReglement implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public isBloque?: boolean,
    ) {
        this.isBloque = false;
    }
}
