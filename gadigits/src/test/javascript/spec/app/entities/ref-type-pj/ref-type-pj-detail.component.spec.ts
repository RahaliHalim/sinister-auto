/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefTypePjDetailComponent } from '../../../../../../main/webapp/app/entities/ref-type-pj/ref-type-pj-detail.component';
import { RefTypePjService } from '../../../../../../main/webapp/app/entities/ref-type-pj/ref-type-pj.service';
import { RefTypePj } from '../../../../../../main/webapp/app/entities/ref-type-pj/ref-type-pj.model';

describe('Component Tests', () => {

    describe('RefTypePj Management Detail Component', () => {
        let comp: RefTypePjDetailComponent;
        let fixture: ComponentFixture<RefTypePjDetailComponent>;
        let service: RefTypePjService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefTypePjDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefTypePjService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefTypePjDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefTypePjDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefTypePjService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefTypePj(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refTypePj).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
