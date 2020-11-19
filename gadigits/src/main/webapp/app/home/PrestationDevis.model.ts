import { BaseEntity } from './../shared';
import {Devis} from '../entities/devis/devis.model';

export class PrestationDevis {
    constructor(
        public devis?: Devis,
    ) {
    }
}
