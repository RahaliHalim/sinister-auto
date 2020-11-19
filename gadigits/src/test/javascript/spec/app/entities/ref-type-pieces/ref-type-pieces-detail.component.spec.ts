/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefTypePiecesDetailComponent } from '../../../../../../main/webapp/app/entities/ref-type-pieces/ref-type-pieces-detail.component';
import { RefTypePiecesService } from '../../../../../../main/webapp/app/entities/ref-type-pieces/ref-type-pieces.service';
import { RefTypePieces } from '../../../../../../main/webapp/app/entities/ref-type-pieces/ref-type-pieces.model';

describe('Component Tests', () => {

    describe('RefTypePieces Management Detail Component', () => {
        let comp: RefTypePiecesDetailComponent;
        let fixture: ComponentFixture<RefTypePiecesDetailComponent>;
        let service: RefTypePiecesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefTypePiecesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefTypePiecesService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefTypePiecesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefTypePiecesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefTypePiecesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefTypePieces(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refTypePieces).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
