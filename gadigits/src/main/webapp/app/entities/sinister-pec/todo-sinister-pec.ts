import { BaseEntity } from '../../shared';
export class TodoPrestationPec {
    constructor(
        public id?: number,
        public prestationId?: number,
        public descPtsChoc?: String,
        public nbrVehicules?: number,
        public prestationReference?: String,
        public modeLibelle?: String,
        public posGaLibelle?: String,
        public userFirstName?: String,
        public userLastName?: String,
        public quotationId?: String,
        public quotationStatus?: String,
        public statusId?: number,
        public statusName?: String    
    ) {
    }
}
