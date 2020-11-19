import { BaseEntity } from './../../shared';
import { RefPack } from '../ref-pack/ref-pack.model';

export class Client implements BaseEntity {
    constructor(
        public id?: number,
        public code?: number,
        public compagnieId?: number,
        public concessionnaireId?: number,
        public concessionnaireLibelle?: string,
        public compagnieNom?: string,
        public contrats?: BaseEntity[],
        public produits?: BaseEntity[],
        public packs?: RefPack[],
        public garanties?: BaseEntity[],
    ) {
    }
}
