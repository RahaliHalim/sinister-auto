import { BaseEntity } from './../../shared';

export class Piece implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public designation?: string,
        public isVetuste?: boolean,
        public typePieceId?: number,
        public modelVoitures?: BaseEntity[],
    ) {
        this.isVetuste = false;
    }
}
