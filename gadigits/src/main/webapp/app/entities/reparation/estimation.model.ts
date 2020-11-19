import { BaseEntity } from '../../shared';

export class Estimation implements BaseEntity {
    constructor(
        public id?: number,
        public url?: string,
     
    ) {
    }
}
