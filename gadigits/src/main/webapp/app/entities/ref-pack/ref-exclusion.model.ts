
import { BaseEntity } from '../../shared';
export class RefExclusion implements BaseEntity {
  
    constructor(
        public id?: number,
        public libelle?: string,
        public etat?: string,
        public state?: string,  
        public deleted?: boolean
    ) {
		this.deleted = false
    }
}
