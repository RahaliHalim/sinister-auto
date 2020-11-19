import { BaseEntity } from '../../shared';

export class RefGrounds implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string
    ) {
    }
}
