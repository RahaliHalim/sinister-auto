/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefZoneGeoDetailComponent } from '../../../../../../main/webapp/app/entities/ref-zone-geo/ref-zone-geo-detail.component';
import { RefZoneGeoService } from '../../../../../../main/webapp/app/entities/ref-zone-geo/ref-zone-geo.service';
import { RefZoneGeo } from '../../../../../../main/webapp/app/entities/ref-zone-geo/ref-zone-geo.model';

describe('Component Tests', () => {

    describe('RefZoneGeo Management Detail Component', () => {
        let comp: RefZoneGeoDetailComponent;
        let fixture: ComponentFixture<RefZoneGeoDetailComponent>;
        let service: RefZoneGeoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefZoneGeoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefZoneGeoService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefZoneGeoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefZoneGeoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefZoneGeoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefZoneGeo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refZoneGeo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
