import { BaseEntity } from "../../shared";

export class UserEntiteFonctionnalite implements BaseEntity{
    constructor(
        public id?: number,
        public userId?: number,
        public userFirstname?: String,
        public userEmail?: String,
        public entiteFonctionnaliteId?: number,
    ){}
}