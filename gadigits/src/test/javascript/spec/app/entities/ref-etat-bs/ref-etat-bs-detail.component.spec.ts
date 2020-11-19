/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefEtatBsDetailComponent } from '../../../../../../main/webapp/app/entities/ref-etat-bs/ref-etat-bs-detail.component';
import { RefEtatBsService } from '../../../../../../main/webapp/app/entities/ref-etat-bs/ref-etat-bs.service';
import { RefEtatBs } from '../../../../../../main/webapp/app/entities/ref-etat-bs/ref-etat-bs.model';

describe('Component Tests', () => {

    describe('RefEtatBs Management Detail Component', () => {
        let comp: RefEtatBsDetailComponent;
        let fixture: ComponentFixture<RefEtatBsDetailComponent>;
        let service: RefEtatBsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefEtatBsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefEtatBsService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefEtatBsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefEtatBsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefEtatBsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefEtatBs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refEtatBs).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
