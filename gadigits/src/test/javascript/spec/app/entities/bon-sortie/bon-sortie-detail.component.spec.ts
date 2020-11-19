/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BonSortieDetailComponent } from '../../../../../../main/webapp/app/entities/bon-sortie/bon-sortie-detail.component';
import { BonSortieService } from '../../../../../../main/webapp/app/entities/bon-sortie/bon-sortie.service';
import { BonSortie } from '../../../../../../main/webapp/app/entities/bon-sortie/bon-sortie.model';

describe('Component Tests', () => {

    describe('BonSortie Management Detail Component', () => {
        let comp: BonSortieDetailComponent;
        let fixture: ComponentFixture<BonSortieDetailComponent>;
        let service: BonSortieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [BonSortieDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BonSortieService,
                    JhiEventManager
                ]
            }).overrideTemplate(BonSortieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BonSortieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BonSortieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BonSortie(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bonSortie).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
