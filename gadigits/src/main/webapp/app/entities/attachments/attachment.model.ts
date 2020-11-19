import { BaseEntity } from '../../shared';

export class Attachment implements BaseEntity {
   constructor(
       public id?: number,
       public label?: string,
       public type?: string,
       public original?: boolean,
       public path?: string,
       public originalName?: string,
       public name?: string,
       public entityName?: string,
       public entityId?: number,
       public creationDate?: Date,
       public createUserId?: number,
       public note?: string,
       public type_Id?: string,
       public creationDateP?: string,
       public originalFr?: string,
       public pieceFile?: File,
       public urlTelechargment?: string,
       public dataUnit?: string,
       public firstNameUser?: string,
       public lastNameUser?: string
   ) {
   }
}
