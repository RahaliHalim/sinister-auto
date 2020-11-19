/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PartnerDetailComponent } from '../../../../../../main/webapp/app/entities/partner/partner-detail.component';
import { PartnerService } from '../../../../../../main/webapp/app/entities/partner/partner.service';
import { Partner } from '../../../../../../main/webapp/app/entities/partner/partner.model';

describe('Component Tests', () => {

    describe('Partner Management Detail Component', () => {
        let comp: PartnerDetailComponent;
        let fixture: ComponentFixture<PartnerDetailComponent>;
        let service: PartnerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PartnerDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PartnerService,
                    JhiEventManager
                ]
            }).overrideTemplate(PartnerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartnerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartnerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Partner(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.partner).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
