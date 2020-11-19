import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ElementMenu } from './element-menu.model';
import { ElementMenuService } from './element-menu.service';

@Component({
    selector: 'jhi-element-menu-detail',
    templateUrl: './element-menu-detail.component.html'
})
export class ElementMenuDetailComponent implements OnInit, OnDestroy {

    elementMenu: ElementMenu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private elementMenuService: ElementMenuService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInElementMenus();
    }

    load(id) {
        this.elementMenuService.find(id).subscribe((elementMenu) => {
            this.elementMenu = elementMenu;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInElementMenus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'elementMenuListModification',
            (response) => this.load(this.elementMenu.id)
        );
    }
}
