/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SinisterPecDetailComponent } from '../../../../../../main/webapp/app/entities/sinister-pec/sinister-pec-detail.component';
import { SinisterPecService } from '../../../../../../main/webapp/app/entities/sinister-pec/sinister-pec.service';
import { SinisterPec } from '../../../../../../main/webapp/app/entities/sinister-pec/sinister-pec.model';

describe('Component Tests', () => {

    describe('SinisterPec Management Detail Component', () => {
        let comp: SinisterPecDetailComponent;
        let fixture: ComponentFixture<SinisterPecDetailComponent>;
        let service: SinisterPecService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [SinisterPecDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SinisterPecService,
                    JhiEventManager
                ]
            }).overrideTemplate(SinisterPecDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SinisterPecDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SinisterPecService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SinisterPec(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sinisterPec).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
