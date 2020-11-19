import { BaseEntity } from './../../shared';
import { Features } from '../../entities/feature/feature.model';


export class ListeFeatureLien {
    constructor(
        public lienWithoutParent?: Features,
        public lien?: Features[]
    ) {
    }
}