/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RefNatureExpertiseDetailComponent } from '../../../../../../main/webapp/app/entities/ref-nature-expertise/ref-nature-expertise-detail.component';
import { RefNatureExpertiseService } from '../../../../../../main/webapp/app/entities/ref-nature-expertise/ref-nature-expertise.service';
import { RefNatureExpertise } from '../../../../../../main/webapp/app/entities/ref-nature-expertise/ref-nature-expertise.model';

describe('Component Tests', () => {

    describe('RefNatureExpertise Management Detail Component', () => {
        let comp: RefNatureExpertiseDetailComponent;
        let fixture: ComponentFixture<RefNatureExpertiseDetailComponent>;
        let service: RefNatureExpertiseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [RefNatureExpertiseDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RefNatureExpertiseService,
                    JhiEventManager
                ]
            }).overrideTemplate(RefNatureExpertiseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RefNatureExpertiseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RefNatureExpertiseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RefNatureExpertise(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.refNatureExpertise).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
