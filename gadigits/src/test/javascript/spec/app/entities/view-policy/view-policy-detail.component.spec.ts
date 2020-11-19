/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ViewPolicyDetailComponent } from '../../../../../../main/webapp/app/entities/view-policy/view-policy-detail.component';
import { ViewPolicyService } from '../../../../../../main/webapp/app/entities/view-policy/view-policy.service';
import { ViewPolicy } from '../../../../../../main/webapp/app/entities/view-policy/view-policy.model';

describe('Component Tests', () => {

    describe('ViewPolicy Management Detail Component', () => {
        let comp: ViewPolicyDetailComponent;
        let fixture: ComponentFixture<ViewPolicyDetailComponent>;
        let service: ViewPolicyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ViewPolicyDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ViewPolicyService,
                    JhiEventManager
                ]
            }).overrideTemplate(ViewPolicyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ViewPolicyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ViewPolicyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ViewPolicy(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.viewPolicy).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
