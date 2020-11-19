import { BaseEntity } from "../../shared";

export class ProfileEntiteFonctionnalite implements BaseEntity{
    constructor(
        public id?: number,
        public profileId?: number,
        public profillibelle?: String,
        public entiteFonctionnaliteId?: number,
    
    ){}
}