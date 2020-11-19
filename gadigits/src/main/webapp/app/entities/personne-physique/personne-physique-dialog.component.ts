import { Component, OnInit, OnDestroy , Output, EventEmitter, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { PersonnePhysique } from './personne-physique.model';
import { PersonnePhysiquePopupService } from './personne-physique-popup.service';
import { PersonnePhysiqueService } from './personne-physique.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';
import { SysVille, SysVilleService } from '../sys-ville';
import { SysGouvernorat } from '../sys-gouvernorat/sys-gouvernorat.model';
import { SysGouvernoratService } from '../sys-gouvernorat/sys-gouvernorat.service';

@Component({
    selector: 'jhi-personne-physique-dialog',
    templateUrl: './personne-physique-dialog.component.html'
})
export class PersonnePhysiqueDialogComponent implements OnInit {
    sysgouvernorats: SysGouvernorat[];
    sysGouvernorat: SysGouvernorat = new SysGouvernorat();
    @Input() personnePhysique: PersonnePhysique = new PersonnePhysique()
    isSaving: boolean;
    gouvernoratId:number;
    users: User[];
    sysVille: SysVille;
    sysvilles: SysVille[];
    @Input() verif = 0 ;
    @Output() change: EventEmitter<Boolean> = new EventEmitter<Boolean>();
    @Output() valueChange = new EventEmitter<any>();
    constructor(
        private alertService: JhiAlertService,
        private personnePhysiqueService: PersonnePhysiqueService,
        private userService: UserService,
        private sysVilleService: SysVilleService,
        private sysGouvernoratService: SysGouvernoratService,
        private route: ActivatedRoute,
        private eventManager: JhiEventManager
    ) {
    }
    ngOnInit() {
        
        this.isSaving = false;
        this.userService.query().subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysVilleService.findAllWithoutPagination().subscribe((res: ResponseWrapper) => { this.sysvilles = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysGouvernoratService.query().subscribe(
            (res: ResponseWrapper) => { this.sysgouvernorats = res.json; }, 
            (res: ResponseWrapper) => this.onError(res.json)
        );
        if ( this.personnePhysique.gouvernorat == null) {
            this.personnePhysique.gouvernorat = new SysGouvernorat();
        }
        if ( this.personnePhysique.villeId != null ) {
            this.findGouvernoratOfVille(this.personnePhysique.villeId)
        }
        this.initPersonnePhysique();
    }
    initPersonnePhysique() {
        this.route.params.subscribe((params) => {
            if (params['idPp']) {
               this.personnePhysiqueService.find(params['idPp']).subscribe((subRes: PersonnePhysique) => {
                    this.personnePhysique = subRes;
                    if ( this.personnePhysique.villeId != null ) {
                        this.personnePhysique.gouvernorat = new SysGouvernorat()
                        this.findGouvernoratOfVille(this.personnePhysique.villeId)
                    }
                }, (subRes: ResponseWrapper) => this.onError(subRes.json));
            } else {
                if ( this.personnePhysique.gouvernorat == null) {
                   this.personnePhysique.gouvernorat = new SysGouvernorat()
                }
            }
        });
    }
    verifRequired(){
        if(this.personnePhysique.nom != null && this.personnePhysique.prenom != null && this.personnePhysique.villeId != null 
            && this.personnePhysique.premTelephone != null  && this.personnePhysique.adresse != null  
           )
       {
           this.verif = 1
           this.valueChange.emit(this.verif);
       }else{
           this.verif = 0;
           this.valueChange.emit(this.verif);
            }
       }
    save() {
        this.isSaving = true;
        if (this.personnePhysique.id !== undefined) {
            this.subscribeToSaveResponse(this.personnePhysiqueService.update(this.personnePhysique));
        } else {
            this.subscribeToSaveResponse(this.personnePhysiqueService.create(this.personnePhysique));
        }
    }
    private subscribeToSaveResponse(result: Observable<PersonnePhysique>) {
        result.subscribe((res: PersonnePhysique) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PersonnePhysique) {
        this.eventManager.broadcast({ name: 'personnePhysiqueListModification', content: 'OK' });
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

    trackUserById(index: number, item: User) {
        return item.id;
    }
    trackSysVilleById(index: number, item: SysVille) {
        return item.id;
    }
    trackGouvernoratById(index: number, item: SysGouvernorat) {
        return item.id;
    }
    listVillesByGouvernoratLieu(id) {
        this.sysGouvernoratService.find(id).subscribe((subRes: SysGouvernorat) => {
            this.sysGouvernorat = subRes;
            this.sysVilleService.findByGouvernorat(this.sysGouvernorat.id).subscribe((subRes1: SysVille[]) => {
                this.sysvilles = subRes1;
                if(subRes1 && subRes1.length > 0) {
                    this.personnePhysique.villeId = subRes1[0].id;
                }
            });
        });

    }
    findGouvernoratOfVille(idVille) {
        this.sysVilleService.find(idVille).subscribe((res: SysVille) => {
            this.sysVille = res;
            this.gouvernoratId =this.sysVille.gouvernoratId
            this.sysGouvernoratService.find(this.sysVille.gouvernoratId).subscribe((subRes: SysGouvernorat) => {
            this.sysGouvernorat = subRes;
           
            })
        }
    )
    }
}

@Component({
    selector: 'jhi-personne-physique-popup',
    template: ''
})
export class PersonnePhysiquePopupComponent implements OnInit, OnDestroy {
    routeSub: any;
    constructor(
        private route: ActivatedRoute,
        private personnePhysiquePopupService: PersonnePhysiquePopupService
    ) { }
    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.personnePhysiquePopupService.open(PersonnePhysiqueDialogComponent as Component, params['id']);
            } else {
                this.personnePhysiquePopupService.open(PersonnePhysiqueDialogComponent as Component);
            }
        });
    }
    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
