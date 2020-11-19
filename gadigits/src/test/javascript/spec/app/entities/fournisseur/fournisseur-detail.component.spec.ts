/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FournisseurDetailComponent } from '../../../../../../main/webapp/app/entities/fournisseur/fournisseur-detail.component';
import { FournisseurService } from '../../../../../../main/webapp/app/entities/fournisseur/fournisseur.service';
import { Fournisseur } from '../../../../../../main/webapp/app/entities/fournisseur/fournisseur.model';

describe('Component Tests', () => {

    describe('Fournisseur Management Detail Component', () => {
        let comp: FournisseurDetailComponent;
        let fixture: ComponentFixture<FournisseurDetailComponent>;
        let service: FournisseurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [FournisseurDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FournisseurService,
                    JhiEventManager
                ]
            }).overrideTemplate(FournisseurDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FournisseurDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FournisseurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Fournisseur(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fournisseur).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
