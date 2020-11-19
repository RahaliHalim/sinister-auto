import { SinisterPec } from '../sinister-pec/sinister-pec.model';
import { BaseEntity } from '../../shared/model/base-entity';

export class AccordPriseCharge {
    constructor(
        public quotationId?: number,
        public sinisterPec?: SinisterPec
    ) {
    }
}
