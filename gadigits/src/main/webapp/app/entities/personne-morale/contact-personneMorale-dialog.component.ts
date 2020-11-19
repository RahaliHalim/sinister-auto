import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Contact } from '../contact/contact.model';
import { ContactPopupService } from '../contact/contact-popup.service';
import { ContactService } from '../contact/contact.service';
import { PersonnePhysique, PersonnePhysiqueService } from '../personne-physique';
import { PersonneMorale, PersonneMoraleService } from '../personne-morale';

import { SysVille, SysVilleService } from '../sys-ville';
import { SysGouvernorat, SysGouvernoratService } from '../sys-gouvernorat';

import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-contact-dialog',
    templateUrl: './contact-personneMorale-dialog.component.html'
})
export class ContactPersonneMoraleDialogComponent implements OnInit {
    personnePhysique: PersonnePhysique = new PersonnePhysique();
    contact: Contact = new Contact();
    isSaving: boolean;
    personnephysiques: PersonnePhysique[];
    sysGouvernorat: SysGouvernorat;
    personneMorales: PersonneMorale[];
    sysvilles: SysVille[];
    gouvernorats: SysGouvernorat[];
    selected: any;
    selectedValue;
    sysVille: SysVille;
    disabled:boolean = true;
    personneMorale: any;
    listContacts: Contact[];
    verifPersonnePhysique: number = 0;
    constructor(
        private sysVilleService: SysVilleService,
        private sysGouvernoratService: SysGouvernoratService,
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
   
       this.contact.personneMoraleId = this.personneMoraleService.id;
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
        this.personneMoraleService.query()
            .subscribe((res: ResponseWrapper) => { this.personneMorales = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.initContact();

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

                        if ( this.personnePhysique.villeId != null ) {
                            this.findGouvernoratOfVille(this.personnePhysique.villeId)
                        }
                    },(subRes: ResponseWrapper) => this.onError(subRes.json));
                    this.verifPersonnePhysique = 1;
            }
            if ( this.personnePhysique.gouvernorat == null) {
                this.personnePhysique.gouvernorat = new SysGouvernorat()
             }
        }, (res: ResponseWrapper) => this.onError(res.json));
       
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }
   onChange(val: any) {
  
   }
   verifRequiredPersonnePhysique(ver){
  this.verifPersonnePhysique = ver;
     
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
         this.personneMoraleService
                    .find(this.contact.personneMoraleId)
                    .subscribe((subRes: PersonneMorale) => {
                        this.personneMorale = subRes;
                        this.contactService.findByPersonneMorale(this.personneMorale.id)
                        .subscribe((res: ResponseWrapper) => { this.listContacts = res.json; })
                    }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                     this.activeModal.dismiss(true);
    }
    private subscribeToSaveResponse(result: Observable<Contact>) {
        result.subscribe((res: Contact) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onSaveSuccess(result: Contact) {
        this.eventManager.broadcast({ name: 'contactListModification', content: 'OK'});
        this.isSaving = false;
        this.personneMoraleService
                    .find(this.contact.personneMoraleId)
                    .subscribe((subRes: PersonneMorale) => {
                        this.personneMorale = subRes;
                        this.contactService.findByPersonneMorale(this.personneMorale.id)
                        .subscribe((res: ResponseWrapper) => { this.listContacts = res.json; })});
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
    trackSysVilleById(index: number, item: SysVille) {
        return item.id;
    }
    trackGouvernoratById(index: number, item: SysGouvernorat) {
        return item.id;
    }
    trackPersonneMoraleById(index: number, item: PersonneMorale) {
        return item.id;
    }
    listVillesByGouvernoratLieu(id) {
        if(id) this.disabled=false;
        this.sysGouvernoratService.find(id).subscribe((subRes: SysGouvernorat) => {
            this.sysGouvernorat = subRes;
            this.sysVilleService.findByGouvernorat(this.sysGouvernorat.id).subscribe((subRes1: SysVille[]) => {
                this.sysvilles = subRes1;
                if(subRes1 && subRes1.length > 0) {
                    this.personneMorale.villeId = subRes1[0].id;
                }

            });
        });
    }
    findGouvernoratOfVille(idVille) {
        this.sysVilleService.find(idVille).subscribe((res: SysVille) => {
            this.sysVille = res;
            this.sysGouvernoratService.find(this.sysVille.gouvernoratId).subscribe((subRes: SysGouvernorat) => {
          
            })
        }
    )
    }
}
@Component({
    selector: 'jhi-contact-popup',
    template: ''
})
export class ContactPersonneMoralePopupComponent implements OnInit, OnDestroy {
    routeSub: any;
    constructor(
        private route: ActivatedRoute,
        private contactPopupService: ContactPopupService
    ) {}
    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contactPopupService
                    .open(ContactPersonneMoraleDialogComponent as Component, params['id']);
            }
           
            else {
                this.contactPopupService
                    .open(ContactPersonneMoraleDialogComponent as Component);
            }
        });
    }
    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
