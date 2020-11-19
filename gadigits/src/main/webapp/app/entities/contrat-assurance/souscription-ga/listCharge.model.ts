import { BaseEntity } from "../../../shared";

export class ListCharge implements BaseEntity {
    constructor(
        public id?: number,
        public nomCharge?: String,
        public prenomCharge?: String
       
    ) { }
}