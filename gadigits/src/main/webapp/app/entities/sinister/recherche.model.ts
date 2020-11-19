import { BaseEntity } from './../../shared';

export class Recherche implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public immatriculation?: string,
        public reference?: string,
        public types?: any,
        public naturePack? : string,
        public zone? : string,
        public refStep? : string,
        public statusPec? : string
       

    ) {
    }
}

