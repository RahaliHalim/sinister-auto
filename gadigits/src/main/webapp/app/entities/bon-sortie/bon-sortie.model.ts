import { BaseEntity } from './../../shared';

export class BonSortie implements BaseEntity {
    constructor(
        public id?: number,
        public numero?: number,
        public isSigne?: boolean,
        public observation?: string,
        public refEtatBsId?: number,
    ) {
        this.isSigne = false;
    }
}
