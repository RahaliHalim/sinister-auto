/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AmendmentTypeDetailComponent } from '../../../../../../main/webapp/app/entities/amendment-type/amendment-type-detail.component';
import { AmendmentTypeService } from '../../../../../../main/webapp/app/entities/amendment-type/amendment-type.service';
import { AmendmentType } from '../../../../../../main/webapp/app/entities/amendment-type/amendment-type.model';

describe('Component Tests', () => {

    describe('AmendmentType Management Detail Component', () => {
        let comp: AmendmentTypeDetailComponent;
        let fixture: ComponentFixture<AmendmentTypeDetailComponent>;
        let service: AmendmentTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [AmendmentTypeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AmendmentTypeService,
                    JhiEventManager
                ]
            }).overrideTemplate(AmendmentTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AmendmentTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmendmentTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AmendmentType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.amendmentType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
