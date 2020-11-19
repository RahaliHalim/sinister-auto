import { BaseEntity } from '../../shared';

export class ReportFrequency implements BaseEntity {
    constructor(
        public id?: number,
        public partnerId?: number,
        public partnerName?: number,
        public usageLabel?: string,
        public incidentNature?: string,
        public contractCount?: number,
        public typeService?: string

     
     
    ) {
        
    }
}