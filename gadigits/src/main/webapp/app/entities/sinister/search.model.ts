
class DataTablesResponse {
    data: any[];
    draw: number;
    recordsFiltered: number;
    recordsTotal: number;
  }

export class Search {
    constructor(
        public partnerId?: number,
        public partnerLabel?: string,
        public startDate?: any,
        public startDateNC?: any,
        public endDate?: any,
        public endDateNC?: any,
        public tugId?: number,
        public tugLabel?: string,
        public dmi?: number,
        public operator?: number,
        public managmentModeId?: number,
        public zoneId?: number,
        public cng?: boolean,
        public zone? : string,
        public packId? : number,
        public idAgent? : number,
        public compagnieId?: number,
        public serviceId?: number,
        public idEtatDossier?: number,
        public agentId?: number,
        public modeGestionId?: number,
        public chargeId? : number,
        public expertId?: number,
        public idZoneReparateur?: number,
        public reparateurId? : number,
         public idEtapePrestation? : number,
         public idStepPec  ? : number,
         public types?: any,
        public immatriculation? : string,
        public reference? : string,
        public assignedToId? : number,
        public statusId? : number,
        public motifAnnulationId? : number,
        public dataTables? : DataTablesResponse ,
        public dataTablesParameters? : any,
        public marqueId?: string,
        public typeChoc?: boolean,
        public minInt?: number,
        public maxInt?: number,
        public typeService? : string,

        
    ) {
    }
}
