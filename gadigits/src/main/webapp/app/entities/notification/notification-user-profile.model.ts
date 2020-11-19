import { BaseEntity } from './../../shared';

export class NotificationUserProfile implements BaseEntity {

    constructor(
        public id?: number,
	    public userProfileId?: number,
	    public userExtraId?: number,
        public notificationId?: number
    ) {
       
    }
}
