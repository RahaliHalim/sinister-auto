/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { JournalDetailComponent } from '../../../../../../main/webapp/app/entities/journal/journal-detail.component';
import { JournalService } from '../../../../../../main/webapp/app/entities/journal/journal.service';
import { Journal } from '../../../../../../main/webapp/app/entities/journal/journal.model';

describe('Component Tests', () => {

    describe('Journal Management Detail Component', () => {
        let comp: JournalDetailComponent;
        let fixture: ComponentFixture<JournalDetailComponent>;
        let service: JournalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [JournalDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    JournalService,
                    JhiEventManager
                ]
            }).overrideTemplate(JournalDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JournalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Journal(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.journal).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
