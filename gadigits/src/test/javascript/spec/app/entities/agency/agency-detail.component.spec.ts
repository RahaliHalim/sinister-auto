/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AgencyDetailComponent } from '../../../../../../main/webapp/app/entities/agency/agency-detail.component';
import { AgencyService } from '../../../../../../main/webapp/app/entities/agency/agency.service';
import { Agency } from '../../../../../../main/webapp/app/entities/agency/agency.model';

describe('Component Tests', () => {

    describe('Agency Management Detail Component', () => {
        let comp: AgencyDetailComponent;
        let fixture: ComponentFixture<AgencyDetailComponent>;
        let service: AgencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [AgencyDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AgencyService,
                    JhiEventManager
                ]
            }).overrideTemplate(AgencyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgencyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Agency(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.agency).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
