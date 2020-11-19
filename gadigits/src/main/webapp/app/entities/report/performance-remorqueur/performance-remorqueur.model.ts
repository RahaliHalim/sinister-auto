import { BaseEntity } from '../../../shared';
export class PerformanceRemorqueur implements BaseEntity {


    constructor(
        public id?: number,
        public referenceGa?: string,
        public compagnieId?: number,
        public compagnie?: string,
        public remorqueurId?: number,
        public remorqueur?: string,
        public immatriculationVehicule?: string,
        public typeService?: string,

        public zoneId?: number,
        public zone?: string,
        public creationDate?: Date ,


        public usageId?: number,
        public usage?: string,
        public count?: number,
       

    
    ) {
    }

}