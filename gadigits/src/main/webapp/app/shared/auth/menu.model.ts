import { BaseEntity } from "../model/base-entity";

export class SubMenu implements BaseEntity {
    constructor(
        
        public id?: number,
        public label?: string,
        public url?:  string,
    ){}
}

export class ElementMenu implements BaseEntity {
    constructor(
        
        public id?: number,
        public label?: string,
        public subMenus?:  SubMenu[],
    ){
        this.subMenus = [];
    }
}