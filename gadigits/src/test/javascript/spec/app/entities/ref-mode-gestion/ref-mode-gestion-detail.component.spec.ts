/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefModeGestionDetailComponent } from '../../../../../../main/webapp/app/entities/ref-mode-gestion/ref-mode-gestion-detail.component';
import { RefModeGestionService } from '../../../../../../main/webapp/app/entities/ref-mode-gestion/ref-mode-gestion.service';
import { RefModeGestion } from '../../../../../../main/webapp/app/entities/ref-mode-gestion/ref-mode-gestion.model';

describe('Component Tests', () => {

    describe('RefModeGestion Management Detail Component', () => {
        let comp: RefModeGestionDetailComponent;
        let fixture: ComponentFixture<RefModeGestionDetailComponent>;
        let service: RefModeGestionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefModeGestionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefModeGestionService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefModeGestionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefModeGestionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefModeGestionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefModeGestion(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refModeGestion).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
