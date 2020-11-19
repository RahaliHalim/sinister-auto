/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefMotifDetailComponent } from '../../../../../../main/webapp/app/entities/ref-motif/ref-motif-detail.component';
import { RefMotifService } from '../../../../../../main/webapp/app/entities/ref-motif/ref-motif.service';
import { RefMotif } from '../../../../../../main/webapp/app/entities/ref-motif/ref-motif.model';

describe('Component Tests', () => {

    describe('RefMotif Management Detail Component', () => {
        let comp: RefMotifDetailComponent;
        let fixture: ComponentFixture<RefMotifDetailComponent>;
        let service: RefMotifService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefMotifDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefMotifService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefMotifDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefMotifDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefMotifService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefMotif(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refMotif).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
