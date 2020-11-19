/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PolicyNatureDetailComponent } from '../../../../../../main/webapp/app/entities/policy-nature/policy-nature-detail.component';
import { PolicyNatureService } from '../../../../../../main/webapp/app/entities/policy-nature/policy-nature.service';
import { PolicyNature } from '../../../../../../main/webapp/app/entities/policy-nature/policy-nature.model';

describe('Component Tests', () => {

    describe('PolicyNature Management Detail Component', () => {
        let comp: PolicyNatureDetailComponent;
        let fixture: ComponentFixture<PolicyNatureDetailComponent>;
        let service: PolicyNatureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PolicyNatureDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PolicyNatureService,
                    JhiEventManager
                ]
            }).overrideTemplate(PolicyNatureDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PolicyNatureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PolicyNatureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PolicyNature(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.policyNature).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
