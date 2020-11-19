import { BaseEntity } from './../../shared';

export enum TypeParamDrools {
    'Pourcentage',
     'Montant'
     
  }

export class ParamsValueDrools implements BaseEntity {
    constructor(
        public id?: number,
        public compagnieId?: number,
        public paramDroolsId?: number,
        public typeParamDrools?: string,
        public minValue?: number,
        public fixValue?: number,
        public paramValue?: number,
        public paramParentId?: number
    ) {
    }
}