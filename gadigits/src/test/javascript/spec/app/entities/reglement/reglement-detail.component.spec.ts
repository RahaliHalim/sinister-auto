/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReglementDetailComponent } from '../../../../../../main/webapp/app/entities/reglement/reglement-detail.component';
import { ReglementService } from '../../../../../../main/webapp/app/entities/reglement/reglement.service';
import { Reglement } from '../../../../../../main/webapp/app/entities/reglement/reglement.model';

describe('Component Tests', () => {

    describe('Reglement Management Detail Component', () => {
        let comp: ReglementDetailComponent;
        let fixture: ComponentFixture<ReglementDetailComponent>;
        let service: ReglementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ReglementDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReglementService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReglementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReglementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReglementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Reglement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reglement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
