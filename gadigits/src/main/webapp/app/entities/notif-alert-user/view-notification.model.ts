import { BaseEntity } from './../../shared';

export class ViewNotification implements BaseEntity {
    constructor(
        public id?: number,
        public destination?: number,
        public translationCode?: string,
        public settings?: string,
        public read?: boolean,
        public type?: string

    ) {
        this.read = false;
    }
}
