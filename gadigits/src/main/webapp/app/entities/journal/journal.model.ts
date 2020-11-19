import { BaseEntity } from './../../shared';

export class Journal implements BaseEntity {
    constructor(
        public id?: number,
        public commentaire?: string,
        public timestamp?: any,
        public sysActionUtilisateurId?: number,
        public sysActionUtilisateurNom?: string,
        public dossierId?: number,
        public dossierReference?: string,
        public prestationId?: number,
        public utilisateur?: string,
        public prestationReference?: string,
        public motifs?: BaseEntity[]
    ) {
    }
}
