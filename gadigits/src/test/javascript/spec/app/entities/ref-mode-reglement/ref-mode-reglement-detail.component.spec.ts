/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefModeReglementDetailComponent } from '../../../../../../main/webapp/app/entities/ref-mode-reglement/ref-mode-reglement-detail.component';
import { RefModeReglementService } from '../../../../../../main/webapp/app/entities/ref-mode-reglement/ref-mode-reglement.service';
import { RefModeReglement } from '../../../../../../main/webapp/app/entities/ref-mode-reglement/ref-mode-reglement.model';

describe('Component Tests', () => {

    describe('RefModeReglement Management Detail Component', () => {
        let comp: RefModeReglementDetailComponent;
        let fixture: ComponentFixture<RefModeReglementDetailComponent>;
        let service: RefModeReglementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefModeReglementDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefModeReglementService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefModeReglementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefModeReglementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefModeReglementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefModeReglement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refModeReglement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
