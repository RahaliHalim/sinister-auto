/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReasonDetailComponent } from '../../../../../../main/webapp/app/entities/reason/reason-detail.component';
import { ReasonService } from '../../../../../../main/webapp/app/entities/reason/reason.service';
import { Reason } from '../../../../../../main/webapp/app/entities/reason/reason.model';

describe('Component Tests', () => {

    describe('Reason Management Detail Component', () => {
        let comp: ReasonDetailComponent;
        let fixture: ComponentFixture<ReasonDetailComponent>;
        let service: ReasonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ReasonDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReasonService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReasonDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReasonDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReasonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Reason(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reason).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
