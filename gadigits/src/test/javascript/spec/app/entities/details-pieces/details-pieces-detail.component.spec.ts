/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DetailsPiecesDetailComponent } from '../../../../../../main/webapp/app/entities/details-pieces/details-pieces-detail.component';
import { DetailsPiecesService } from '../../../../../../main/webapp/app/entities/details-pieces/details-pieces.service';
import { DetailsPieces } from '../../../../../../main/webapp/app/entities/details-pieces/details-pieces.model';

describe('Component Tests', () => {

    describe('DetailsPieces Management Detail Component', () => {
        let comp: DetailsPiecesDetailComponent;
        let fixture: ComponentFixture<DetailsPiecesDetailComponent>;
        let service: DetailsPiecesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [DetailsPiecesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DetailsPiecesService,
                    JhiEventManager
                ]
            }).overrideTemplate(DetailsPiecesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetailsPiecesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailsPiecesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DetailsPieces(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.detailsPieces).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
