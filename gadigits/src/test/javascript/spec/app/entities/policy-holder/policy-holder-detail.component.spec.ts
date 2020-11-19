/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PolicyHolderDetailComponent } from '../../../../../../main/webapp/app/entities/policy-holder/policy-holder-detail.component';
import { PolicyHolderService } from '../../../../../../main/webapp/app/entities/policy-holder/policy-holder.service';
import { PolicyHolder } from '../../../../../../main/webapp/app/entities/policy-holder/policy-holder.model';

describe('Component Tests', () => {

    describe('PolicyHolder Management Detail Component', () => {
        let comp: PolicyHolderDetailComponent;
        let fixture: ComponentFixture<PolicyHolderDetailComponent>;
        let service: PolicyHolderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PolicyHolderDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PolicyHolderService,
                    JhiEventManager
                ]
            }).overrideTemplate(PolicyHolderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PolicyHolderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PolicyHolderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PolicyHolder(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.policyHolder).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
