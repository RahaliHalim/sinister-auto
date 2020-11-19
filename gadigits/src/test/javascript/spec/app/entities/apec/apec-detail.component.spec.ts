/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ApecDetailComponent } from '../../../../../../main/webapp/app/entities/apec/apec-detail.component';
import { ApecService } from '../../../../../../main/webapp/app/entities/apec/apec.service';
import { Apec } from '../../../../../../main/webapp/app/entities/apec/apec.model';

describe('Component Tests', () => {

    describe('Apec Management Detail Component', () => {
        let comp: ApecDetailComponent;
        let fixture: ComponentFixture<ApecDetailComponent>;
        let service: ApecService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ApecDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ApecService,
                    JhiEventManager
                ]
            }).overrideTemplate(ApecDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApecDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApecService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Apec(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.apec).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
