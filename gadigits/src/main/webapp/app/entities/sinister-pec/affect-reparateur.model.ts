import { SinisterPec } from '../sinister-pec/sinister-pec.model';
import { Reparateur } from '../reparateur/reparateur.model';
import { VehiculeAssure } from '../vehicule-assure/vehicule-assure.model';
import { Assure } from '../assure/assure.model';

export class AffectReparateur {
    constructor(
        public vehiculeAssure?: VehiculeAssure,
        public sinisterPec?: SinisterPec,
        public assureNom?: String,
        public assureGsm?: String,
        public reparateurNom?: String,
        public reparateurContactNom?: String,
        public reparateurContactGsm?: String,
        public reparateurAdresse?: String
    ) {
    }
}
