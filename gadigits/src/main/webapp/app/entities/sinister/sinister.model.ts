import { SinisterPrestation } from './sinister-prestation.model';
import { SinisterPec } from '../sinister-pec/sinister-pec.model';
import { Governorate } from '../governorate/governorate.model';
import { BaseEntity } from '../../shared';

export enum EtatSinister {
    'Encours',
    'Cloturé',
    'Annulé'
}

const enum IncidentNature {
    'PANNE',
    'ACCIDENT'
}

export class Sinister implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public deleted?: boolean,
        public phone?: string,
        public passengerCount?: number,
        public conductorFirstName?: string,
        public conductorLastName?: string,
        public governorateId?: number,
        public governorateLabel?: string,
        public locationId?: number,
        public locationLabel?: string,
        public nature?: string,
        public incidentDate?: any,
        public contractId?: number,
        public vehicleId?: number,
        public insuredId?: number,
        public vehicleRegistration?: string,
        public insuredFullName?: string,
        public statusId?: number,
        public statusLabel?: string,
        public partnerId?: number,
        public partnerLabel?: string,
        public agencyId?: number,
        public agencyLabel?: string,
        public usageLabel?: string,
        public frequencyRate?: number,
        public sinisterPec?: SinisterPec,
        public prestations?: SinisterPrestation[],
        public creationDate?: any,
        public closureDate?: any,
        public updateDate?: any,
        public assignmentDate?: any,
        public gouvernorat?: Governorate,
        public creationUserId?: number,
        public closureUserId?: number,
        public updateUserId?: number,
        public assignedToId?: number,
        public serviceTypesStr?: string,
        public isAssure ?:boolean,
        public naturePanneId?: number
    ) {
        this.deleted = false;
        this.prestations = [];
        this.isAssure = false;
    }
}
