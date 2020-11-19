import { BaseEntity } from './../../shared';
export class PecMotifDecision implements BaseEntity {
    constructor(
        public id?: number,
        public sinisterPecId?: number,
        public refMotifId?: number,
        public refModeGestionId?: number
    ) {
    }
}
