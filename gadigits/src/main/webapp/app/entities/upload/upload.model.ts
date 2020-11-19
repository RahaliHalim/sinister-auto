import { BaseEntity } from './../../shared';
import { Attachment } from '../attachments/attachment.model';
export class Upload implements BaseEntity {
    constructor(
        public id?: number,
        public referentielId?: number,
        public libelle?: string,
        public attachment?: Attachment,
        public firstName?: string,
        public lastName?: string,
        public dateUpload?: Date,
        public dateUpload2?: string,
        public attachmentName?: string,
        public attachment64?: string,
        public originalName?: string
    ) {
    }
}