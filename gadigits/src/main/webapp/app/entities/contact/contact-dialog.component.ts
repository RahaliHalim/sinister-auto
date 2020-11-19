import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Contact } from './contact.model';
import { ContactPopupService } from './contact-popup.service';
import { ContactService } from './contact.service';
import { PersonnePhysique, PersonnePhysiqueService } from '../personne-physique';
import { PersonneMorale, PersonneMoraleService } from '../personne-morale';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-contact-dialog',
    templateUrl: './contact-dialog.component.html'
})
export class ContactDialogComponent implements OnInit {
    personnePhysique: PersonnePhysique = new PersonnePhysique();
    contact: Contact = new Contact();
    isSaving: boolean;

    personnephysiques: PersonnePhysique[];

    personneMorales: PersonneMorale[];
    selected: any;
    selectedValue;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private contactService: ContactService,
        private personnePhysiqueService: PersonnePhysiqueService,
        private personneMoraleService: PersonneMoraleService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
    ) {
    }
    ngOnInit() {
        this.isSaving = false;
        this.personnePhysiqueService
            .query({filter: 'contact-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.contact.personnePhysiqueId) {
                    this.personnephysiques = res.json;
                } else {
                    this.personnePhysiqueService
                        .find(this.contact.personnePhysiqueId)
                        .subscribe((subRes: PersonnePhysique) => {
                            this.personnephysiques = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this. initContact();
}
    initContact() {
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contactService
                    .find(params['id'])
                    .subscribe((subRes: Contact) => {
                        this.contact = subRes;
                        this.initPersonnePhysique();
                    }, (subRes: ResponseWrapper) => this.onError(subRes.json));
            } else {
                this.initPersonnePhysique();
            }
        });
    }
initPersonnePhysique() {
        this.personnePhysiqueService.query().subscribe((res: ResponseWrapper) => {
            if (!this.contact.personnePhysiqueId ) {
               this.personnePhysique = new PersonnePhysique();
            }else {
                this.personnePhysiqueService
                    .find(this.contact.personnePhysiqueId)
                    .subscribe((subRes: PersonnePhysique) => {
                        this.personnePhysique = subRes;
                        this.personnephysiques = [subRes].concat(res.json);
                    }, (subRes: ResponseWrapper) => this.onError(subRes.json));
            }
        }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }
onChange(val: any) {
    this.contact.personneMoraleId = val;
  }
    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    save() {
        this.isSaving = true;
        if (this.contact.id !== undefined) {
            this.subscribeperToSaveResponse(
                this.personnePhysiqueService.update(this.personnePhysique));
        } else {
            this.subscribeperToSaveResponse(
                this.personnePhysiqueService.create(this.personnePhysique));
        }
    }
    private subscribeperToSaveResponse(result: Observable<PersonnePhysique>) {
        result.subscribe((res: PersonnePhysique) =>
            this.onPerSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onPerSaveSuccess(result: PersonnePhysique) {
        this.contact.personnePhysiqueId = result.id;
        if (this.contact.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contactService.update(this.contact));
        } else {
            this.subscribeToSaveResponse(
                this.contactService.create(this.contact));
        }
    }

    private subscribeToSaveResponse(result: Observable<Contact>) {
        result.subscribe((res: Contact) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Contact) {
        this.eventManager.broadcast({ name: 'contactListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(true);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackPersonnePhysiqueById(index: number, item: PersonnePhysique) {
        return item.id;
    }

  trackPersonneMoraleById(index: number, item: PersonneMorale) {
        return item.id;
    }

}

@Component({
    selector: 'jhi-contact-popup',
    template: ''
})
export class ContactPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactPopupService: ContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['idPp'] ) {
                this.contactPopupService
                    .open(ContactDialogComponent as Component, params['idPp']);
            } else {
                this.contactPopupService
                    .open(ContactDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
