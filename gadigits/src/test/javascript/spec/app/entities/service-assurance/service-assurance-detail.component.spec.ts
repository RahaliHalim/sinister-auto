/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ServiceAssuranceDetailComponent } from '../../../../../../main/webapp/app/entities/service-assurance/service-assurance-detail.component';
import { ServiceAssuranceService } from '../../../../../../main/webapp/app/entities/service-assurance/service-assurance.service';
import { ServiceAssurance } from '../../../../../../main/webapp/app/entities/service-assurance/service-assurance.model';

describe('Component Tests', () => {

    describe('ServiceAssurance Management Detail Component', () => {
        let comp: ServiceAssuranceDetailComponent;
        let fixture: ComponentFixture<ServiceAssuranceDetailComponent>;
        let service: ServiceAssuranceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ServiceAssuranceDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ServiceAssuranceService,
                    JhiEventManager
                ]
            }).overrideTemplate(ServiceAssuranceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServiceAssuranceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServiceAssuranceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ServiceAssurance(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.serviceAssurance).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
