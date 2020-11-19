/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PolicyReceiptStatusDetailComponent } from '../../../../../../main/webapp/app/entities/policy-receipt-status/policy-receipt-status-detail.component';
import { PolicyReceiptStatusService } from '../../../../../../main/webapp/app/entities/policy-receipt-status/policy-receipt-status.service';
import { PolicyReceiptStatus } from '../../../../../../main/webapp/app/entities/policy-receipt-status/policy-receipt-status.model';

describe('Component Tests', () => {

    describe('PolicyReceiptStatus Management Detail Component', () => {
        let comp: PolicyReceiptStatusDetailComponent;
        let fixture: ComponentFixture<PolicyReceiptStatusDetailComponent>;
        let service: PolicyReceiptStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PolicyReceiptStatusDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PolicyReceiptStatusService,
                    JhiEventManager
                ]
            }).overrideTemplate(PolicyReceiptStatusDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PolicyReceiptStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PolicyReceiptStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PolicyReceiptStatus(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.policyReceiptStatus).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
