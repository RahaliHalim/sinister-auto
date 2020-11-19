/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LoueurDetailComponent } from '../../../../../../main/webapp/app/entities/loueur/loueur-detail.component';
import { LoueurService } from '../../../../../../main/webapp/app/entities/loueur/loueur.service';
import { Loueur } from '../../../../../../main/webapp/app/entities/loueur/loueur.model';

describe('Component Tests', () => {

    describe('Loueur Management Detail Component', () => {
        let comp: LoueurDetailComponent;
        let fixture: ComponentFixture<LoueurDetailComponent>;
        let service: LoueurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [LoueurDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LoueurService,
                    JhiEventManager
                ]
            }).overrideTemplate(LoueurDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LoueurDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoueurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Loueur(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.loueur).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
