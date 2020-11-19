/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GrilleDetailComponent } from '../../../../../../main/webapp/app/entities/grille/grille-detail.component';
import { GrilleService } from '../../../../../../main/webapp/app/entities/grille/grille.service';
import { Grille } from '../../../../../../main/webapp/app/entities/grille/grille.model';

describe('Component Tests', () => {

    describe('Grille Management Detail Component', () => {
        let comp: GrilleDetailComponent;
        let fixture: ComponentFixture<GrilleDetailComponent>;
        let service: GrilleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [GrilleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GrilleService,
                    JhiEventManager
                ]
            }).overrideTemplate(GrilleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrilleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrilleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Grille(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.grille).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
