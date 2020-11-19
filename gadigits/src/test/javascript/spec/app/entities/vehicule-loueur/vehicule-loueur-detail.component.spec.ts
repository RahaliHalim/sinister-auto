/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VehiculeLoueurDetailComponent } from '../../../../../../main/webapp/app/entities/vehicule-loueur/vehicule-loueur-detail.component';
import { VehiculeLoueurService } from '../../../../../../main/webapp/app/entities/vehicule-loueur/vehicule-loueur.service';
import { VehiculeLoueur } from '../../../../../../main/webapp/app/entities/vehicule-loueur/vehicule-loueur.model';

describe('Component Tests', () => {

    describe('VehiculeLoueur Management Detail Component', () => {
        let comp: VehiculeLoueurDetailComponent;
        let fixture: ComponentFixture<VehiculeLoueurDetailComponent>;
        let service: VehiculeLoueurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [VehiculeLoueurDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VehiculeLoueurService,
                    JhiEventManager
                ]
            }).overrideTemplate(VehiculeLoueurDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VehiculeLoueurDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehiculeLoueurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VehiculeLoueur(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.vehiculeLoueur).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
