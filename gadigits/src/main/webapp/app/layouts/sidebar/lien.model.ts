import { BaseEntity } from './../../shared';
import {Lien} from '../../entities/lien/lien.model'

export class ListeLien {
    constructor(
        public lienWithoutParent?: Lien,
        public lien?: Lien[]
    ) {
    }
}
