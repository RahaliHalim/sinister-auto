import { BaseEntity } from './../../shared';

export class Features implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public name?: string,
	    public url?: string,
        public entityId?: number,
        public parent?: number

    
    ) {
    }
}