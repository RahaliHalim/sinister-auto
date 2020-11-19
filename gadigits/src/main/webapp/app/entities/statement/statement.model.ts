import { BaseEntity } from './../../shared';

export class Statement implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public path?: string,
        public tugId?: number,
        public tugLabel?: string,
        public creationDate?: any,
        public notes?: string,
        public step?: number,
    ) {
    }
}
