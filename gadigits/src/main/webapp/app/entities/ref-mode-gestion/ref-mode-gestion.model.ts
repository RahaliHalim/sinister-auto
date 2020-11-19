import { BaseEntity } from './../../shared';

export class RefModeGestion implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public description?: string,
        public garanties?: BaseEntity[],
    
        
    ) {
    }
}
