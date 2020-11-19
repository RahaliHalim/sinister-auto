import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { Principal } from '../';
import { LoginModalService } from '../login/login-modal.service';
import { StateStorageService } from './state-storage.service';
import { UserExtraService } from '../../entities/user-extra';


@Injectable()
export class UserRouteAccessService implements CanActivate {

    constructor(private router: Router,
                private loginModalService: LoginModalService,
                private userExtraService: UserExtraService,
                private principal: Principal,
                private stateStorageService: StateStorageService) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Promise<boolean> {

        const authorities = route.data['authorities'];
        const entity = route.data['entity'];
        const functionality = route.data['functionality'];
        if (!authorities || authorities.length === 0) {
            return true;
        }
        return this.checkLogin(authorities, entity, functionality, state.url);;
    }

    checkLogin(authorities: string[], entity: number, functionality: number, url: string): Promise<boolean> {
        const principal = this.principal;
        const userService = this.userExtraService;
        return Promise.resolve(principal.identity().then((account) => {          
            if(account && principal.hasAnyAuthorityDirect(authorities)) { // authenticated and has necessery role
                if(entity && functionality) {
                    return Promise.resolve(userService.isUserHasFunctionality(entity, functionality).toPromise().then((res) => {
                        if(res) {
                            return true;
                        }
                        this.stateStorageService.storeUrl(url);
                        this.router.navigate(['accessdenied']).then(() => {
                            console.log('User has no access to this resource !');
                        });
                        return false;
                    }));
                }
                return true;
            } 
            this.stateStorageService.storeUrl(url);
            this.router.navigate(['accessdenied']).then(() => {
                // only show the login dialog, if the user hasn't logged in yet
                if (!account) {
                    this.loginModalService.open();
                }
            });
            return false;
        }));
    }
}
