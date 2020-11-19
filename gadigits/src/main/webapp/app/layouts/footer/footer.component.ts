import { Component, OnInit } from '@angular/core';
import { Principal, Account } from '../../shared';
import { JhiEventManager } from 'ng-jhipster';
@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html'
})
export class FooterComponent implements OnInit {
    private currentYear: string;
    public copyright: string;
    account: Account;
    constructor(private eventManager: JhiEventManager, public principal: Principal) {
            this.currentYear = '';
            this.copyright = '';
        }

    ngOnInit() {
        this.currentYear = new Date().getFullYear().toString();
        this.copyright = this.currentYear;
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
}
