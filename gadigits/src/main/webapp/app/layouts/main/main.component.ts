import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, RoutesRecognized } from '@angular/router';

import { JhiLanguageHelper, StateStorageService } from '../../shared';
import { CookieService } from 'ngx-cookie';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html',
    styleUrls: [
        'main.css'
    ]
})
export class JhiMainComponent implements OnInit {

    constructor(
        private jhiLanguageHelper: JhiLanguageHelper,
        private router: Router,
        private cookieService: CookieService
    ) {
    }

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = (routeSnapshot.data && routeSnapshot.data['pageTitle']) ? routeSnapshot.data['pageTitle'] : 'auxiliumApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        this.router.events.subscribe((event) => {
            if (event instanceof NavigationEnd) {
                this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
        });
    }
}
