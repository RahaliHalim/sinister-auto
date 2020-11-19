import { BaseEntity } from './../../shared';

export class RefNotifAlert implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public code?: string,
        public sendingDate?: any,
        public type?: any,
        public isClosed?: boolean,
        public translationCode?: string
    ) {
        this.isClosed = false;
    }
}
