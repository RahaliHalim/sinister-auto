/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefMaterielDetailComponent } from '../../../../../../main/webapp/app/entities/ref-materiel/ref-materiel-detail.component';
import { RefMaterielService } from '../../../../../../main/webapp/app/entities/ref-materiel/ref-materiel.service';
import { RefMateriel } from '../../../../../../main/webapp/app/entities/ref-materiel/ref-materiel.model';

describe('Component Tests', () => {

    describe('RefMateriel Management Detail Component', () => {
        let comp: RefMaterielDetailComponent;
        let fixture: ComponentFixture<RefMaterielDetailComponent>;
        let service: RefMaterielService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefMaterielDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefMaterielService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefMaterielDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefMaterielDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefMaterielService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefMateriel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refMateriel).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
