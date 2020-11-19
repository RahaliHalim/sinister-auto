import { BaseEntity } from './../../shared';

export class Group implements BaseEntity{
    constructor(
        public id?: number,
        public name?: string,
        public idClient?: number,
        public idProduct?: number,
        public productName?: string,
        public users?: BaseEntity[]
    ) { }
}