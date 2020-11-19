/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ElementMenuDetailComponent } from '../../../../../../main/webapp/app/entities/element-menu/element-menu-detail.component';
import { ElementMenuService } from '../../../../../../main/webapp/app/entities/element-menu/element-menu.service';
import { ElementMenu } from '../../../../../../main/webapp/app/entities/element-menu/element-menu.model';

describe('Component Tests', () => {

    describe('ElementMenu Management Detail Component', () => {
        let comp: ElementMenuDetailComponent;
        let fixture: ComponentFixture<ElementMenuDetailComponent>;
        let service: ElementMenuService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ElementMenuDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ElementMenuService,
                    JhiEventManager
                ]
            }).overrideTemplate(ElementMenuDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ElementMenuDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElementMenuService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ElementMenu(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.elementMenu).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
