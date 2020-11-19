import { BaseEntity } from './../../shared';

export class Cellule implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public liens?: BaseEntity[],
    ) {
    }
}
