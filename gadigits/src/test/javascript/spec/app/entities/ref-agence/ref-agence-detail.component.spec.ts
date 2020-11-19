/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefAgenceDetailComponent } from '../../../../../../main/webapp/app/entities/ref-agence/ref-agence-detail.component';
import { RefAgenceService } from '../../../../../../main/webapp/app/entities/ref-agence/ref-agence.service';
import { RefAgence } from '../../../../../../main/webapp/app/entities/ref-agence/ref-agence.model';

describe('Component Tests', () => {

    describe('RefAgence Management Detail Component', () => {
        let comp: RefAgenceDetailComponent;
        let fixture: ComponentFixture<RefAgenceDetailComponent>;
        let service: RefAgenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefAgenceDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefAgenceService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefAgenceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefAgenceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefAgenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefAgence(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refAgence).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
