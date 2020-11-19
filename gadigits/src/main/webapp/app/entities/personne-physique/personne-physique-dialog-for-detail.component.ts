import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PersonnePhysique } from './personne-physique.model';
import { PersonnePhysiqueService } from './personne-physique.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';
import { SysVille, SysVilleService } from '../sys-ville';
import { SysGouvernorat } from '../sys-gouvernorat/sys-gouvernorat.model';
import { SysGouvernoratService } from '../sys-gouvernorat/sys-gouvernorat.service';

@Component({
    selector: 'jhi-personne-physique-dialog-for-detail',
    templateUrl: './personne-physique-dialog-for-detail.component.html'
})
export class PersonnePhysiqueDialogForDetailComponent implements OnInit {

    sysgouvernorats: SysGouvernorat[];
    sysGouvernorat: SysGouvernorat = new SysGouvernorat();

    @Input() personnePhysique: PersonnePhysique = new PersonnePhysique()
    isSaving: boolean;

    users: User[];
    sysVille: SysVille;
    sysvilles: SysVille[];

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
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
            this.sysVilleService.findAllWithoutPagination()
            .subscribe((res: ResponseWrapper) => { this.sysvilles = res.json }, (res: ResponseWrapper) => this.onError(res.json));
            this.sysGouvernoratService.query()
                .subscribe((res: ResponseWrapper) => { this.sysgouvernorats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
                if ( this.personnePhysique.gouvernorat == null) {
                    this.personnePhysique.gouvernorat = new SysGouvernorat()
                }
                if ( this.personnePhysique.villeId != null ) {
                    this.findGouvernoratOfVille(this.personnePhysique.villeId)
                }
                this.initPersonnePhysique()
    }
    initPersonnePhysique() {
        this.route.params.subscribe((params) => {
           if (params['idPp']) {
               this.personnePhysiqueService
                   .find(params['idPp'])
                   .subscribe((subRes: PersonnePhysique) => {
                       this.personnePhysique = subRes;
                       if ( this.personnePhysique.villeId != null ) {
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
            });
        });

    }
    findGouvernoratOfVille(idVille) {
        this.sysVilleService.find(idVille).subscribe((res: SysVille) => {
            this.sysVille = res;
            this.sysGouvernoratService.find(this.sysVille.gouvernoratId).subscribe((subRes: SysGouvernorat) => {
            this.personnePhysique.gouvernorat = subRes
            })
        }
    )
    }
}
