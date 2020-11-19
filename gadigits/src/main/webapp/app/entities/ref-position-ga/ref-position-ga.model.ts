import { BaseEntity } from './../../shared';

export class RefPositionGa implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
    ) {
    }
}
