/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VehicleDetailComponent } from '../../../../../../main/webapp/app/entities/vehicle/vehicle-detail.component';
import { VehicleService } from '../../../../../../main/webapp/app/entities/vehicle/vehicle.service';
import { Vehicle } from '../../../../../../main/webapp/app/entities/vehicle/vehicle.model';

describe('Component Tests', () => {

    describe('Vehicle Management Detail Component', () => {
        let comp: VehicleDetailComponent;
        let fixture: ComponentFixture<VehicleDetailComponent>;
        let service: VehicleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [VehicleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VehicleService,
                    JhiEventManager
                ]
            }).overrideTemplate(VehicleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VehicleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehicleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Vehicle(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.vehicle).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
