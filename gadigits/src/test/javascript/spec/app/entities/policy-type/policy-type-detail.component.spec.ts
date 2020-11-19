/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PolicyTypeDetailComponent } from '../../../../../../main/webapp/app/entities/policy-type/policy-type-detail.component';
import { PolicyTypeService } from '../../../../../../main/webapp/app/entities/policy-type/policy-type.service';
import { PolicyType } from '../../../../../../main/webapp/app/entities/policy-type/policy-type.model';

describe('Component Tests', () => {

    describe('PolicyType Management Detail Component', () => {
        let comp: PolicyTypeDetailComponent;
        let fixture: ComponentFixture<PolicyTypeDetailComponent>;
        let service: PolicyTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PolicyTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PolicyTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(PolicyTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PolicyTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PolicyTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PolicyType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.policyType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
