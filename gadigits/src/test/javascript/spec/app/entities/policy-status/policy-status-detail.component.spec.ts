/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PolicyStatusDetailComponent } from '../../../../../../main/webapp/app/entities/policy-status/policy-status-detail.component';
import { PolicyStatusService } from '../../../../../../main/webapp/app/entities/policy-status/policy-status.service';
import { PolicyStatus } from '../../../../../../main/webapp/app/entities/policy-status/policy-status.model';

describe('Component Tests', () => {

    describe('PolicyStatus Management Detail Component', () => {
        let comp: PolicyStatusDetailComponent;
        let fixture: ComponentFixture<PolicyStatusDetailComponent>;
        let service: PolicyStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PolicyStatusDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PolicyStatusService,
                    JhiEventManager
                ]
            }).overrideTemplate(PolicyStatusDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PolicyStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PolicyStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PolicyStatus(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.policyStatus).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
