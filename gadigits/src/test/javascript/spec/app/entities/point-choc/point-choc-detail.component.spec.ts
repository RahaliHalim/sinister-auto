/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PointChocDetailComponent } from '../../../../../../main/webapp/app/entities/point-choc/point-choc-detail.component';
import { PointChocService } from '../../../../../../main/webapp/app/entities/point-choc/point-choc.service';
import { PointChoc } from '../../../../../../main/webapp/app/entities/point-choc/point-choc.model';

describe('Component Tests', () => {

    describe('PointChoc Management Detail Component', () => {
        let comp: PointChocDetailComponent;
        let fixture: ComponentFixture<PointChocDetailComponent>;
        let service: PointChocService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PointChocDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PointChocService,
                    JhiEventManager
                ]
            }).overrideTemplate(PointChocDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PointChocDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PointChocService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PointChoc(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pointChoc).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
