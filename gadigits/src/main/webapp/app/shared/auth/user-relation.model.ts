import { BaseEntity } from "../model/base-entity";

export class UserRelation implements BaseEntity{
    constructor(
        
        public id?: number,

        public strongUserId?: string,
        public strongUserFirstname?: string,
        public strongUserLastname?: string,

        public weakUserId?: number,
        public weakUserFirstname?: string,
        public weakUserLastname?: string,

        public type?: string,
    ){}
}