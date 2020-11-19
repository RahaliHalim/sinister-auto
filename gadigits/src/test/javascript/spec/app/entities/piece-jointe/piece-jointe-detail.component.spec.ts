/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PieceJointeDetailComponent } from '../../../../../../main/webapp/app/entities/piece-jointe/piece-jointe-detail.component';
import { PieceJointeService } from '../../../../../../main/webapp/app/entities/piece-jointe/piece-jointe.service';
import { PieceJointe } from '../../../../../../main/webapp/app/entities/piece-jointe/piece-jointe.model';

describe('Component Tests', () => {

    describe('PieceJointe Management Detail Component', () => {
        let comp: PieceJointeDetailComponent;
        let fixture: ComponentFixture<PieceJointeDetailComponent>;
        let service: PieceJointeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PieceJointeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PieceJointeService,
                    JhiEventManager
                ]
            }).overrideTemplate(PieceJointeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PieceJointeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PieceJointeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PieceJointe(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pieceJointe).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
