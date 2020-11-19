import { BaseEntity } from './../../shared';
import { NotificationUserProfile } from './notification-user-profile.model';

export class JhiNotification implements BaseEntity {

    constructor(
        public id?: number,
	    public commentaire?: string,
	    public jhiDate?: any,
        public jhiType?: string,
        public isClosed?: boolean,
        public notificationUserProfile?: NotificationUserProfile[],
        
    ) {
        this.isClosed = false;
        this.notificationUserProfile = [];
    }
}
