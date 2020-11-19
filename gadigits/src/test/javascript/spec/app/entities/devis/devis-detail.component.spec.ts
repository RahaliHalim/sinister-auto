/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DevisDetailComponent } from '../../../../../../main/webapp/app/entities/devis/devis-detail.component';
import { DevisService } from '../../../../../../main/webapp/app/entities/devis/devis.service';
import { Devis } from '../../../../../../main/webapp/app/entities/devis/devis.model';

describe('Component Tests', () => {

    describe('Devis Management Detail Component', () => {
        let comp: DevisDetailComponent;
        let fixture: ComponentFixture<DevisDetailComponent>;
        let service: DevisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [DevisDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DevisService,
                    JhiEventManager
                ]
            }).overrideTemplate(DevisDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DevisDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DevisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Devis(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.devis).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
