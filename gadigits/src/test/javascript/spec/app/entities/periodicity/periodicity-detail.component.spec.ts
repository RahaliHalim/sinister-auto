/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PeriodicityDetailComponent } from '../../../../../../main/webapp/app/entities/periodicity/periodicity-detail.component';
import { PeriodicityService } from '../../../../../../main/webapp/app/entities/periodicity/periodicity.service';
import { Periodicity } from '../../../../../../main/webapp/app/entities/periodicity/periodicity.model';

describe('Component Tests', () => {

    describe('Periodicity Management Detail Component', () => {
        let comp: PeriodicityDetailComponent;
        let fixture: ComponentFixture<PeriodicityDetailComponent>;
        let service: PeriodicityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PeriodicityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PeriodicityService,
                    JhiEventManager
                ]
            }).overrideTemplate(PeriodicityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeriodicityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodicityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Periodicity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.periodicity).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
