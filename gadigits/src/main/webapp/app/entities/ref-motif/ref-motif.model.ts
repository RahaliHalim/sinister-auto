import { BaseEntity } from './../../shared';
import { Motifs, EtatMotifs } from '../../constants/app.constants';

export class RefMotif implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public authorityName?: string,
        public journals?: BaseEntity[],
        public typeMotif?: Motifs,
        public etatMotif?: EtatMotifs 
    ) {
    }
}
