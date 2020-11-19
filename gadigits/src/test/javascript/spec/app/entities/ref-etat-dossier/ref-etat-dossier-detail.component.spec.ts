/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefEtatDossierDetailComponent } from '../../../../../../main/webapp/app/entities/ref-etat-dossier/ref-etat-dossier-detail.component';
import { RefEtatDossierService } from '../../../../../../main/webapp/app/entities/ref-etat-dossier/ref-etat-dossier.service';
import { RefEtatDossier } from '../../../../../../main/webapp/app/entities/ref-etat-dossier/ref-etat-dossier.model';

describe('Component Tests', () => {

    describe('RefEtatDossier Management Detail Component', () => {
        let comp: RefEtatDossierDetailComponent;
        let fixture: ComponentFixture<RefEtatDossierDetailComponent>;
        let service: RefEtatDossierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefEtatDossierDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefEtatDossierService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefEtatDossierDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefEtatDossierDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefEtatDossierService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefEtatDossier(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refEtatDossier).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
