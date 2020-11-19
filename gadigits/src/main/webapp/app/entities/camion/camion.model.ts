import { RefRemorqueur } from './../ref-remorqueur/ref-remorqueur.model';
import { RefTypeService } from './../ref-type-service/ref-type-service.model';
import { BaseEntity } from '../../shared';

export class Camion implements BaseEntity {
    constructor(
        public id?: number,
        public immatriculation?: string,
        public serviceTypes?: RefTypeService[],
        public hasHabillage?: boolean,
        public refTugId?: number,
        public refTug?: RefRemorqueur,
        public serviceTypesStr?: string,
        public statusLabel?: string
    ) {
        this.hasHabillage = false;
        this.serviceTypes = [];
        this.serviceTypesStr = '';
    }
}
