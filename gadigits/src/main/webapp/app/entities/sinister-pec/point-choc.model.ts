import { BaseEntity } from './../../shared';

export class PointChoc implements BaseEntity {
    constructor(
        public id?: number,
        public sinisterPecId?: number,
        public toit?: boolean,
        public avant?: boolean,
        public arriere?: boolean,
        public arriereGauche ?: boolean,
        public lateraleGauche ?: boolean,
        public lateraleGaucheAvant  ?: boolean,
        public arriereDroite ?: boolean,
        public lateraledroite ?: boolean,
        public lateraleDroiteAvant ?: boolean,
        public lateraleDroiteArriere ?: boolean,
        public avantGauche ?: boolean,
        public avantDroite ?: boolean,
        public retroviseurGauche ?: boolean,
        public retroviseurDroite ?: boolean,
        public lunetteArriere ?: boolean,
        public pareBriseAvant ?: boolean,
        public vitreAvantGauche ?: boolean,
        public vitreAvantDroit  ?: boolean,
        public vitreArriereGauche ?: boolean,
        public vitreArriereDroit ?: boolean,
        public triangleArriereGauche ?: boolean,
        public triangleArriereDroit  ?: boolean,
        public lateraleGauchearriere ?: boolean,
        


    ) {
        this.toit = false;
        this.avant = false;
        this.arriere = false;
        this.arriereGauche = false;
        this.lateraleGauche = false;
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
        this.vitreArriereDroit = false;
        this.vitreAvantGauche = false;
        this.triangleArriereGauche = false;
        this.triangleArriereDroit = false;
        this.lateraleGauchearriere = false;
        this.vitreArriereGauche = false;
        this.vitreAvantDroit = false;

    }
}
