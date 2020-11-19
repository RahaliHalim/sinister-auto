import { BaseEntity } from '../../shared';

export class ViewSinister implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public incidentDate?: any,
        public registrationNumber?: string,
        public fullName?: string,
        public nature?: string,
        public serviceTypes?: string,
        public creationDate?: any,
    ) {
    }
}
