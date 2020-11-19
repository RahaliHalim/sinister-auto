/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReparateurDetailComponent } from '../../../../../../main/webapp/app/entities/reparateur/reparateur-detail.component';
import { ReparateurService } from '../../../../../../main/webapp/app/entities/reparateur/reparateur.service';
import { Reparateur } from '../../../../../../main/webapp/app/entities/reparateur/reparateur.model';

describe('Component Tests', () => {

    describe('Reparateur Management Detail Component', () => {
        let comp: ReparateurDetailComponent;
        let fixture: ComponentFixture<ReparateurDetailComponent>;
        let service: ReparateurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ReparateurDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReparateurService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReparateurDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReparateurDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReparateurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Reparateur(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reparateur).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
