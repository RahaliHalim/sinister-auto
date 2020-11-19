/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ObservationDetailComponent } from '../../../../../../main/webapp/app/entities/observation/observation-detail.component';
import { ObservationService } from '../../../../../../main/webapp/app/entities/observation/observation.service';
import { Observation } from '../../../../../../main/webapp/app/entities/observation/observation.model';

describe('Component Tests', () => {

    describe('Observation Management Detail Component', () => {
        let comp: ObservationDetailComponent;
        let fixture: ComponentFixture<ObservationDetailComponent>;
        let service: ObservationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ObservationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ObservationService,
                    JhiEventManager
                ]
            }).overrideTemplate(ObservationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ObservationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObservationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Observation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.observation).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
