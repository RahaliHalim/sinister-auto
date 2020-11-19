/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProduitDetailComponent } from '../../../../../../main/webapp/app/entities/produit/produit-detail.component';
import { ProduitService } from '../../../../../../main/webapp/app/entities/produit/produit.service';
import { Produit } from '../../../../../../main/webapp/app/entities/produit/produit.model';

describe('Component Tests', () => {

    describe('Produit Management Detail Component', () => {
        let comp: ProduitDetailComponent;
        let fixture: ComponentFixture<ProduitDetailComponent>;
        let service: ProduitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ProduitDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProduitService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProduitDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProduitDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProduitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Produit(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.produit).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
