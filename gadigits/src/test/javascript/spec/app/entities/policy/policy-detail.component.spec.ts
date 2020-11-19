/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PolicyDetailComponent } from '../../../../../../main/webapp/app/entities/policy/policy-detail.component';
import { PolicyService } from '../../../../../../main/webapp/app/entities/policy/policy.service';
import { Policy } from '../../../../../../main/webapp/app/entities/policy/policy.model';

describe('Component Tests', () => {

    describe('Policy Management Detail Component', () => {
        let comp: PolicyDetailComponent;
        let fixture: ComponentFixture<PolicyDetailComponent>;
        let service: PolicyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PolicyDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PolicyService,
                    JhiEventManager
                ]
            }).overrideTemplate(PolicyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PolicyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PolicyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Policy(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.policy).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
