import { BaseEntity } from './../../shared';


export class ParamsDrools implements BaseEntity {

    constructor(

        public id?: number,
        public parametre?: number,
        public dafaultValue?: number
        
    ) {
    }
}