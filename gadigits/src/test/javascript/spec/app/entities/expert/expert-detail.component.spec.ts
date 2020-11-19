/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ExpertDetailComponent } from '../../../../../../main/webapp/app/entities/expert/expert-detail.component';
import { ExpertService } from '../../../../../../main/webapp/app/entities/expert/expert.service';
import { Expert } from '../../../../../../main/webapp/app/entities/expert/expert.model';

describe('Component Tests', () => {

    describe('Expert Management Detail Component', () => {
        let comp: ExpertDetailComponent;
        let fixture: ComponentFixture<ExpertDetailComponent>;
        let service: ExpertService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ExpertDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ExpertService,
                    JhiEventManager
                ]
            }).overrideTemplate(ExpertDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExpertDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExpertService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Expert(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.expert).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
