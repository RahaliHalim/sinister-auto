/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefNatureContratDetailComponent } from '../../../../../../main/webapp/app/entities/ref-nature-contrat/ref-nature-contrat-detail.component';
import { RefNatureContratService } from '../../../../../../main/webapp/app/entities/ref-nature-contrat/ref-nature-contrat.service';
import { RefNatureContrat } from '../../../../../../main/webapp/app/entities/ref-nature-contrat/ref-nature-contrat.model';

describe('Component Tests', () => {

    describe('RefNatureContrat Management Detail Component', () => {
        let comp: RefNatureContratDetailComponent;
        let fixture: ComponentFixture<RefNatureContratDetailComponent>;
        let service: RefNatureContratService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefNatureContratDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefNatureContratService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefNatureContratDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefNatureContratDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefNatureContratService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefNatureContrat(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refNatureContrat).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
