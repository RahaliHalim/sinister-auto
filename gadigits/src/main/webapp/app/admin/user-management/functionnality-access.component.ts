import { Component, OnInit, OnDestroy, Output, EventEmitter, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { User, UserService, ResponseWrapper } from '../../shared';
import { ProfileAccessService, ProfileAccess, EntityProfileAccess } from '../../entities/profile-access';

@Component({
    selector: 'jhi-functionnality-access',
    templateUrl: './functionnality-access.component.html',
     })
export class FunctionnalityAccessComponent implements OnInit{

    @Input() accesss;
    @Input() view;
    @Output() accesssChange: EventEmitter<any> = new EventEmitter<any>();

    constructor(
        private alertService: JhiAlertService,
        private userService: UserService,
        private route: ActivatedRoute,
    ) {
    }

    ngOnInit() {
        let isSaving = false;
        console.log('_________________________________1');
        console.log(this.accesss);
        
        
    }
}    