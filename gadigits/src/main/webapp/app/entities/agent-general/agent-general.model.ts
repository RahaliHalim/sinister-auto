import { BaseEntity } from './../../shared';

export class AgentGeneral implements BaseEntity {
    constructor(
        public id?: number,
        public isBloque?: boolean,
        public personnePhysiqueId?: number,
        public personnePhysiqueNom?: string,
        //public agenceId?: number,
        public serviceAssuranceId?: number,
        public dossiers?: BaseEntity[],
        public agences?: any[],
    ) {
        this.isBloque = false;
    }
}
