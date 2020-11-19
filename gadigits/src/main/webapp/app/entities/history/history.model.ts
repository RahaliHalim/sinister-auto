import { BaseEntity } from '../../shared';
export class ChangeValue implements BaseEntity {
    constructor(
        public id?: number,
        public nameAttribute?: String,
        public lastValue?: String,
        public newValue?: String,
        public translateNameAttribute?: String,
   
    ) {
    }
}
export class History implements BaseEntity {
   constructor(
       public id?: number,
       public entityName?: string,
       public entityId?: number,
       public operationName?: string,
       public operationDate?: any,
       public userId?: number,
       public firstName?: string,
       public lastName?: string,
       public actionLabel?: string,
       public actionId?: number,
       public descriptiveOfChange?: string,
       public changeValues ?: ChangeValue[],
       public descriptionOfHistorization ?: string,
       public creation_date?: Date ,
       public modification_date?: Date,
       public typeDevis?: string,
       public typeAccord?: string

       
   ) {
     this.changeValues = [];
     this.descriptiveOfChange = ' ';

   }
}


export class HistoryPec implements BaseEntity {
    constructor(
        public id?: number,
        public entityName?: string,
        public entityId?: number,
        public operationName?: string,
        public operationDate?: any,
        public userId?: number,
        public firstName?: string,
        public lastName?: string,
        public actionLabel?: string,
        public actionId?: number,
        public descriptiveOfChange?: string,
        public changeValues ?: ChangeValue[],
        public descriptionOfHistorization ?: string,
        public creation_date?: Date ,
        public modification_date?: Date,
        public typeDevis?: string,
        public typeAccord?: string
 
        
    ) {
      this.changeValues = [];
      this.descriptiveOfChange = ' ';
 
    }
 }