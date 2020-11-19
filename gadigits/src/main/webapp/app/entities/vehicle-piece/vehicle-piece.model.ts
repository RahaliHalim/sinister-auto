import { BaseEntity } from './../../shared';

export class VehiclePiece implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public reference?: string,
        public label?: string,
        public source?: string,
        public active?: boolean,
        public vetuste?: boolean,
        public graphicAreaId?: number,
        public functionalGroupId?: number,
        public natureId?: number,
        public type?: number,
        public typeId?: number,
        public isNew?: boolean,

        //public typeLabel?: string
    ) {
        this.active = false;
        this.vetuste = false;
    }
}
