import { BaseEntity } from './../../shared';
import { Attachment } from '../attachments/attachment.model';
export class RefBareme implements BaseEntity {
    constructor(
        public id?: number,
        public code?: number,
        public responsabiliteX?: number,
        public responsabiliteY?: number,
        public description?: string,
        public attachment?: Attachment,
        public title?: string,
        public attachment64?: string,
        public attachmentName?: string,
        public nomImage?: string
    ) {

    }
}
