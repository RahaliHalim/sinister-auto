import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { ProfileService } from '../profiles/profile.service';
import { LienService } from '../../entities/lien/lien.service';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from '../../shared';
import { VERSION, DEBUG_INFO_ENABLED } from '../../app.constants';
import { JhiEventManager } from 'ng-jhipster';
import {ListeLien} from './lien.model';
@Component({
    selector: 'jhi-sidebar',
    templateUrl: './sidebar.component.html'
})
export class SidebarComponent implements OnInit {
    account: Account;
    selectedValue;
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    public isActiveSession: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    listeLiensParametrage: any;
    listeLiensAssistance: any;
    listeLiensPEC: any;
    lienNotParent: any[];
    listeLiens: any;
    lienPrincipal: any;
    expandedToggle: any;
    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        public principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private lienService: LienService,
        private router: Router
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = false;
        this.isActiveSession = true;
    }

    ngOnInit() {
        console.log('_________________________________________1112');
        
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
        this.isActiveSession = true;
        this.registerSessionTimeout();
        this.registerAuthenticationSuccess();
        this.principal.newInitSidebar();
    }
    changeLanguage(languageKey: string) {
      this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.account = this.principal.getCurrentAccount();
            this.isActiveSession = true;
            this.principal.newInitSidebar();
        });
    }
    registerSessionTimeout() {
        this.eventManager.subscribe('sessionWasExpired', () => {
            this.isActiveSession = false;
        });
    }
    isAuthenticated() {
        //console.log('_________________________________________1113');
        return this.principal.isAuthenticated();
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }
 }
