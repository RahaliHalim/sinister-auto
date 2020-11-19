import { BaseEntity } from './../../shared';

export class GroupDetails implements BaseEntity{
    constructor(
        public id?: number,
        public groupId?: number,
        public grouName?: string,
        public refModeGestionId?: number,
        public refModeGestionName?: string,
        public clientId?: number,
        public clientName?: string
    ) { }
}