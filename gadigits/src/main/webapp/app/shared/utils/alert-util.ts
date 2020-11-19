import { Injectable } from "@angular/core";
import { JhiAlertService } from 'ng-jhipster';

@Injectable()
export class AlertUtil {

    constructor(private alertService: JhiAlertService) { }

    addError(message: string) {
        if (message) {
            this.alertService.addAlert({
                type: 'danger',
                msg: message,
                params: null,
                timeout: 0,
                toast: this.alertService.isToast(),
                position: null
            }, []);
        }
    }
}
