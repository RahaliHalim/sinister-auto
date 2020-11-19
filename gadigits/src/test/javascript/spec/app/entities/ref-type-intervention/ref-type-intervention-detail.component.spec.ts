/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefTypeInterventionDetailComponent } from '../../../../../../main/webapp/app/entities/ref-type-intervention/ref-type-intervention-detail.component';
import { RefTypeInterventionService } from '../../../../../../main/webapp/app/entities/ref-type-intervention/ref-type-intervention.service';
import { RefTypeIntervention } from '../../../../../../main/webapp/app/entities/ref-type-intervention/ref-type-intervention.model';

describe('Component Tests', () => {

    describe('RefTypeIntervention Management Detail Component', () => {
        let comp: RefTypeInterventionDetailComponent;
        let fixture: ComponentFixture<RefTypeInterventionDetailComponent>;
        let service: RefTypeInterventionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefTypeInterventionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefTypeInterventionService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefTypeInterventionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefTypeInterventionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefTypeInterventionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefTypeIntervention(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refTypeIntervention).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
