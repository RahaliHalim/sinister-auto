/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefBaremeDetailComponent } from '../../../../../../main/webapp/app/entities/ref-bareme/ref-bareme-detail.component';
import { RefBaremeService } from '../../../../../../main/webapp/app/entities/ref-bareme/ref-bareme.service';
import { RefBareme } from '../../../../../../main/webapp/app/entities/ref-bareme/ref-bareme.model';

describe('Component Tests', () => {

    describe('RefBareme Management Detail Component', () => {
        let comp: RefBaremeDetailComponent;
        let fixture: ComponentFixture<RefBaremeDetailComponent>;
        let service: RefBaremeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefBaremeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefBaremeService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefBaremeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefBaremeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefBaremeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefBareme(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refBareme).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
