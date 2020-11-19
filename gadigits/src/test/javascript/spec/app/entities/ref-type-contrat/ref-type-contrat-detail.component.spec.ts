/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefTypeContratDetailComponent } from '../../../../../../main/webapp/app/entities/ref-type-contrat/ref-type-contrat-detail.component';
import { RefTypeContratService } from '../../../../../../main/webapp/app/entities/ref-type-contrat/ref-type-contrat.service';
import { RefTypeContrat } from '../../../../../../main/webapp/app/entities/ref-type-contrat/ref-type-contrat.model';

describe('Component Tests', () => {

    describe('RefTypeContrat Management Detail Component', () => {
        let comp: RefTypeContratDetailComponent;
        let fixture: ComponentFixture<RefTypeContratDetailComponent>;
        let service: RefTypeContratService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefTypeContratDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefTypeContratService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefTypeContratDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefTypeContratDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefTypeContratService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefTypeContrat(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refTypeContrat).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
