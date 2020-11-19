import { JhiHttpInterceptor, JhiEventManager } from 'ng-jhipster';
import { RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Injector } from '@angular/core';
import { AuthServerProvider } from '../../shared/auth/auth-session.service';
import { StateStorageService } from '../../shared/auth/state-storage.service';
import { LoginModalService } from '../../shared/login/login-modal.service';
import { Router } from '@angular/router';

export class AuthExpiredInterceptor extends JhiHttpInterceptor {

    constructor(private injector: Injector, private eventManager: JhiEventManager, private router: Router,
        private stateStorageService: StateStorageService) {
        super();
    }

    requestIntercept(options?: RequestOptionsArgs): RequestOptionsArgs {
        return options;
    }

    responseIntercept(observable: Observable<Response>): Observable<Response> {
        return <Observable<Response>> observable.catch((error) => {
            console.log("----------------------------------------------------------------------------10");
            console.log(error);
            console.log("----------------------------------------------------------------------------10");
            if (error.status === 401 && error.text() !== '' && error.json().path && error.json().path.indexOf('/api/account') === -1) {
                const authServerProvider = this.injector.get(AuthServerProvider);
                const destination = this.stateStorageService.getDestinationState();
                
                if(destination) {
                    const to = destination.destination;
                    const toParams = destination.params;
                    if (to.name === 'accessdenied') {
                        this.stateStorageService.storePreviousState(to.name, toParams);
                    }
                }
                authServerProvider.logout();
                const loginServiceModal = this.injector.get(LoginModalService);
                console.log("----------------------------------------------------------------------------2");
                this.eventManager.broadcast({
                    name: 'sessionWasExpired',
                    content: 'Session Timeout'
                });
                console.log("----------------------------------------------------------------------------3");
                loginServiceModal.open();

            }
            return Observable.throw(error);
        });
    }
}
