import {BaseEntity} from './../../shared';
import {PersonneMorale} from '../personne-morale';

import { VisAVis } from './../vis-a-vis/vis-a-vis.model';
export class RefRemorqueur implements BaseEntity {
  constructor(
    public id?: number,

    public isBloque?: boolean,
    public isConventionne?: boolean,
    public isDelete?: boolean,
    public nombreCamion?: number,
    public personneMorale?: PersonneMorale,
    public societeId?: number,
    public hasHabillage?: boolean,
    public societeRaisonSociale?: string,
    public gouvernoratId?: number,
    public libelleGouvernorat?: string,
    public reclamation?: string,
    public isFree?: boolean,
    public codeCategorie?: string,
    public vatRate?: number,
    public telephone?: string,
    public latitude?: number,
    public longitude?: number,
    public isConnected?: boolean,
    public visAViss?: VisAVis[],
  ) {
    this.hasHabillage = false;
    this.isDelete = false;
    this.isBloque = false;
    this.isFree = true;
    this.visAViss=[];
  }
}
