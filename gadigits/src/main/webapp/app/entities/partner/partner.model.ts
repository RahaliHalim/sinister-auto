import { BaseEntity } from './../../shared';
import { VisAVis } from './../vis-a-vis/vis-a-vis.model';
import { Attachment} from './../attachments/attachment.model'
export class Partner implements BaseEntity {
    constructor(
        public id?: number,
        public companyName?: string,
        public phone?: string,
        public address?: string,
        public tradeRegister?: string,
        public taxIdentificationNumber?: string,
        public foreignCompany?: boolean,
        public foreignCompanyStr?: string,
        public genre?: number,
        public active?: boolean,
        public activeStr?: string,
        public attachment?: Attachment,
        public visAViss?: VisAVis[],
        public companyLogoAttachment64?: string,
        public companyLogoAttachmentName?: string,
        public concessionnaireLogoAttachment64?: string,
        public concessionnaireLogoAttachmentName?: string,
        public conventionne?: boolean,
        public attachmentOriginalName?: string,
        public attachmentName?: string,
    
    ) {
        this.foreignCompany = false;
        this.active = false;
        this.visAViss = [];
    }
}
