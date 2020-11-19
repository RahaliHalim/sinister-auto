/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SysActionUtilisateurDetailComponent } from '../../../../../../main/webapp/app/entities/sys-action-utilisateur/sys-action-utilisateur-detail.component';
import { SysActionUtilisateurService } from '../../../../../../main/webapp/app/entities/sys-action-utilisateur/sys-action-utilisateur.service';
import { SysActionUtilisateur } from '../../../../../../main/webapp/app/entities/sys-action-utilisateur/sys-action-utilisateur.model';

describe('Component Tests', () => {

    describe('SysActionUtilisateur Management Detail Component', () => {
        let comp: SysActionUtilisateurDetailComponent;
        let fixture: ComponentFixture<SysActionUtilisateurDetailComponent>;
        let service: SysActionUtilisateurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [SysActionUtilisateurDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SysActionUtilisateurService,
                    JhiEventManager
                ]
            }).overrideTemplate(SysActionUtilisateurDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SysActionUtilisateurDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SysActionUtilisateurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SysActionUtilisateur(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sysActionUtilisateur).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
