import { BaseEntity } from "../../../shared";

export class   SouscritpionGa implements BaseEntity {
    constructor(
        public id?: number,
        public compagnieId?: number,
        public compagnie?: String,
        public numeroContrat?: String,
        public zoneId?: number,
        public zone?: String,
        public packId?: number,
        public pack? : String,
        public primeHt?: number,
        public primeTtc?: number,
        public partRea?: number,
        public serviceId?: number,
        public service? : String,
        public Idcharge?: number,
        public Charge?: String






    ) { }
}