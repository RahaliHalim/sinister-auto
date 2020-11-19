import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Contact, ContactService } from '../contact';
import { PersonneMorale } from './personne-morale.model';
import { PersonneMoralePopupService } from './personne-morale-popup.service';
import { PersonneMoraleService } from './personne-morale.service';
import { RefRemorqueurService, RefRemorqueur } from '../ref-remorqueur';
import { AssureService, Assure } from '../assure';
import { RefCompagnieService, RefCompagnie } from '../ref-compagnie';
import { ResponseWrapper } from '../../shared';
import { SysVille, SysVilleService } from '../sys-ville';
import { Subscription } from 'rxjs/Rx';
import { SysGouvernorat } from '../sys-gouvernorat/sys-gouvernorat.model';
import { ContratAssurance, ContratAssuranceService } from '../contrat-assurance';
import { SysGouvernoratService } from '../sys-gouvernorat/sys-gouvernorat.service';
@Component({
    selector: 'jhi-personne-morale-dialog-for-detail',
    templateUrl: './personne-morale-dialog-for-detail.component.html'
})
export class PersonneMoraleDialogForDetailComponent implements OnInit {
   
    sysgouvernorats: SysGouvernorat[];
    sysGouvernorat: SysGouvernorat;
   
    @Input() personneMorale: PersonneMorale = new PersonneMorale();
   
    isSaving: boolean;
    sysvilles: SysVille[];
    sysVille: SysVille;
    
    
    listContacts: Contact[];
    eventSubscriber: Subscription;
    contact: any;
    contratAssurance: any;
    assure: any;
    var: any;
    refRemorqueur: any;
    refCompagnie: any;
    test: any;
    constructor(
        private alertService: JhiAlertService,
        private personneMoraleService: PersonneMoraleService,
        private contactService: ContactService,
        private assureService: AssureService,
        private refRemorqueurService: RefRemorqueurService,
        private contratAssuranceService: ContratAssuranceService,
        private refCompagnieService: RefCompagnieService,
        private sysVilleService: SysVilleService,
        private sysGouvernoratService: SysGouvernoratService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
    ) {
    }
    ngOnInit() {
        this.loadAllContact();
        this.isSaving = false;
        this.sysVilleService.findAllWithoutPagination()
        .subscribe((res: ResponseWrapper) => { this.sysvilles = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysGouvernoratService.query()
            .subscribe((res: ResponseWrapper) => { this.sysgouvernorats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
            if ( this.personneMorale.gouvernorat == null) {
                this.personneMorale.gouvernorat = new SysGouvernorat()
            }
            if ( this.personneMorale.villeId != null ) {
                this.findGouvernoratOfVille(this.personneMorale.villeId)
            }
            this.initPersonneMorale();
            this.registerChangeInContacts();

        }
     initPersonneMorale() {
         this.route.params.subscribe((params) => {
            if (params['idPm']) {
                this.personneMoraleService
                    .find(params['idPm'])
                    .subscribe((subRes: PersonneMorale) => {
                        this.personneMorale = subRes;
                        if ( this.personneMorale.villeId != null ) {
                            this.findGouvernoratOfVille(this.personneMorale.villeId)
                        }
                        if(this.personneMorale.id != null && this.personneMorale.id != undefined){
                        this.contactService.findByPersonneMorale(this.personneMorale.id)
                            .subscribe((res: ResponseWrapper) => { this.listContacts = res.json;
                            this.eventSubscriber = this.eventManager.subscribe('contactListModification', (response) => this.listContacts)})
                    }
                    }, (subRes: ResponseWrapper) => this.onError(subRes.json));
            } else {
                if ( this.personneMorale.gouvernorat == null) {
                    this.personneMorale.gouvernorat = new SysGouvernorat()
                }
            }
        });
    }

    trackRefGarantieContractuelleById(index: number, item: Contact) {
        return item.id;
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

    saveContact() {
        this.isSaving = true;
        if (this.contact.id !== undefined) {
            this.subscribeToSaveResponseContact(
                this.contactService.update(this.contact));
        } else {
            this.subscribeToSaveResponseContact(
                this.contactService.create(this.contact));
        }
    }

    private subscribeToSaveResponseContact(result: Observable<Contact>) {
        result.subscribe((res: Contact) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    loadAllContact() {
        if(this.personneMorale.id != null && this.personneMorale.id != undefined){
        this.contactService.findByPersonneMorale(this.personneMorale.id)
            .subscribe((res: ResponseWrapper) => { this.listContacts = res.json; })
        }
    }

    registerChangeInContacts() {
        this.eventSubscriber = this.eventManager.subscribe('contactListModification', (response) => this.loadAllContact());
    }

    save() {
        this.isSaving = true;
        if (this.personneMorale.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personneMoraleService.update(this.personneMorale));
        } else {
            this.subscribeToSaveResponse(
                this.personneMoraleService.create(this.personneMorale));
            this.test = this.personneMorale.id;
        }
    }

    affecterContact(idPersonneMorale) {
        this.contact.personneMoraleId = idPersonneMorale;
    }

    private subscribeToSaveResponse(result: Observable<PersonneMorale>) {
        result.subscribe((res: PersonneMorale) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PersonneMorale) {
        this.eventManager.broadcast({ name: 'personneMoraleListModification', content: 'OK' });
        this.isSaving = false;
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

    trackSysVilleById(index: number, item: SysVille) {
        return item.id;
    }
    trackGouvernoratById(index: number, item: SysGouvernorat) {
        return item.id;
    }
    trackContactsId(index: number, item: Contact) {
        return item.id
    }

    listVillesByGouvernoratLieu(id) {
        this.sysGouvernoratService.find(id).subscribe((subRes: SysGouvernorat) => {
            this.sysGouvernorat = subRes;
            this.sysVilleService.findByGouvernorat(this.sysGouvernorat.id).subscribe((subRes1: SysVille[]) => {
                this.sysvilles = subRes1;
            });
        });
    }
    findGouvernoratOfVille(idVille) {
        this.sysVilleService.find(idVille).subscribe((res: SysVille) => {
            this.sysVille = res;
            this.sysGouvernoratService.find(this.sysVille.gouvernoratId).subscribe((subRes: SysGouvernorat) => {
            this.personneMorale.gouvernorat = subRes
            })
        }
    )
    }
}
