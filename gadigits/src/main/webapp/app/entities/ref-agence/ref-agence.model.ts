import { BaseEntity } from './../../shared';
import { SysGouvernorat } from '../sys-gouvernorat/sys-gouvernorat.model';

export class RefAgence implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public telephone?: string,
        public adresse?: string,
        public compagnieId?: number,
        public villeId?: number,
        public code?: number,
        public gouvernorat?: SysGouvernorat,
    ) {
    }
}
