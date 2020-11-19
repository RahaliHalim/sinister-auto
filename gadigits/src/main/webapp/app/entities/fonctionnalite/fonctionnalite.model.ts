import { BaseEntity } from "../../shared";

export class Fonctionnalite implements BaseEntity{
    constructor(
        public id?: number,
        public label?: string,
        public name?: string,
	    public url?: string,
        public parent?: number
    ){}
}