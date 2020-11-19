import { BaseEntity } from './../../shared';
import { RefNotifAlert } from '../ref-notif-alert/ref-notif-alert.model';

export class NotifAlertUser implements BaseEntity {
    constructor(
        public id?: number,
        public source?: number,
        public destination?: number,
        public profil?: number,
        public notification?: RefNotifAlert,
        public entityName?: string,
        public entityId?: number,
        public sendingDate?: any,
        public settings?: string,
        public read?: boolean,
        public objJson?: string,
    ) {
        this.read = false;
    }
}
