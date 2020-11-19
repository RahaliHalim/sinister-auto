/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonnePhysiqueDetailComponent } from '../../../../../../main/webapp/app/entities/personne-physique/personne-physique-detail.component';
import { PersonnePhysiqueService } from '../../../../../../main/webapp/app/entities/personne-physique/personne-physique.service';
import { PersonnePhysique } from '../../../../../../main/webapp/app/entities/personne-physique/personne-physique.model';

describe('Component Tests', () => {

    describe('PersonnePhysique Management Detail Component', () => {
        let comp: PersonnePhysiqueDetailComponent;
        let fixture: ComponentFixture<PersonnePhysiqueDetailComponent>;
        let service: PersonnePhysiqueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [PersonnePhysiqueDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PersonnePhysiqueService,
                    JhiEventManager
                ]
            }).overrideTemplate(PersonnePhysiqueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonnePhysiqueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonnePhysiqueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PersonnePhysique(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.personnePhysique).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
