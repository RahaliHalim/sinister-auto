/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefPositionGaDetailComponent } from '../../../../../../main/webapp/app/entities/ref-position-ga/ref-position-ga-detail.component';
import { RefPositionGaService } from '../../../../../../main/webapp/app/entities/ref-position-ga/ref-position-ga.service';
import { RefPositionGa } from '../../../../../../main/webapp/app/entities/ref-position-ga/ref-position-ga.model';

describe('Component Tests', () => {

    describe('RefPositionGa Management Detail Component', () => {
        let comp: RefPositionGaDetailComponent;
        let fixture: ComponentFixture<RefPositionGaDetailComponent>;
        let service: RefPositionGaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefPositionGaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefPositionGaService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefPositionGaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefPositionGaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefPositionGaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefPositionGa(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refPositionGa).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
