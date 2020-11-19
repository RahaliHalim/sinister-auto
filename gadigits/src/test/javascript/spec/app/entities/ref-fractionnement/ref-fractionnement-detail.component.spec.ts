/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefFractionnementDetailComponent } from '../../../../../../main/webapp/app/entities/ref-fractionnement/ref-fractionnement-detail.component';
import { RefFractionnementService } from '../../../../../../main/webapp/app/entities/ref-fractionnement/ref-fractionnement.service';
import { RefFractionnement } from '../../../../../../main/webapp/app/entities/ref-fractionnement/ref-fractionnement.model';

describe('Component Tests', () => {

    describe('RefFractionnement Management Detail Component', () => {
        let comp: RefFractionnementDetailComponent;
        let fixture: ComponentFixture<RefFractionnementDetailComponent>;
        let service: RefFractionnementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefFractionnementDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefFractionnementService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefFractionnementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefFractionnementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefFractionnementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefFractionnement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refFractionnement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
