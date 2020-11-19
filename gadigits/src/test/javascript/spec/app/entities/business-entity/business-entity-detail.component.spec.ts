/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BusinessEntityDetailComponent } from '../../../../../../main/webapp/app/entities/business-entity/business-entity-detail.component';
import { BusinessEntityService } from '../../../../../../main/webapp/app/entities/business-entity/business-entity.service';
import { BusinessEntity } from '../../../../../../main/webapp/app/entities/business-entity/business-entity.model';

describe('Component Tests', () => {

    describe('BusinessEntity Management Detail Component', () => {
        let comp: BusinessEntityDetailComponent;
        let fixture: ComponentFixture<BusinessEntityDetailComponent>;
        let service: BusinessEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [BusinessEntityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BusinessEntityService,
                    JhiEventManager
                ]
            }).overrideTemplate(BusinessEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BusinessEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BusinessEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BusinessEntity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.businessEntity).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
