import { BaseEntity } from './../../shared';

export class PointChoc implements BaseEntity {
    constructor(
        public id?: number,
        public sinisterPecId?: number,
        public toit?: boolean,
        public avant?: boolean,
        public arriere?: boolean,
        public arriereGauche?: boolean,
        public lateraleGauche?: boolean,
        public lateraleGaucheAvant?: boolean,
        public arriereDroite?: boolean,
        public lateraledroite?: boolean,
        public lateraleDroiteAvant?: boolean,
        public lateraleDroiteArriere?: boolean,
        public avantGauche?: boolean,
        public avantDroite?: boolean,
        public retroviseurGauche?: boolean,
        public retroviseurDroite?: boolean,
        public lunetteArriere?: boolean,
        public pareBriseAvant?: boolean,
        public triangleArriereGauche?: boolean,
        public triangleArriereDroit?: boolean,
        public vitreAvantDroit?: boolean,
        public vitreAvantGauche?: boolean,
        public vitreArriereDroit?: boolean,
        public vitreArriereGauche?: boolean,
        public lateraleGauchearriere?: boolean
    ) {
        this.toit = false;
        this.avant = false;
        this.arriere = false;
        this.arriereGauche = false;
        this.lateraleGauche = false;
        this.lateraleGauchearriere = false;
        this.lateraleGaucheAvant = false;
        this.arriereDroite = false;
        this.lateraledroite = false;
        this.lateraleDroiteAvant = false;
        this.lateraleDroiteArriere = false;
        this.avantGauche = false;
        this.avantDroite = false;
        this.retroviseurGauche = false;
        this.retroviseurDroite = false;
        this.lunetteArriere = false;
        this.pareBriseAvant = false;
        this.triangleArriereGauche = false;
        this.triangleArriereDroit = false;
        this.vitreAvantDroit = false;
        this.vitreArriereGauche = false;
        this.vitreArriereDroit = false;
        this.vitreAvantGauche = false;
    }
}
