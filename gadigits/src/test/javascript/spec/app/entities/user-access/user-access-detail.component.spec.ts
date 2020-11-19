/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserAccessDetailComponent } from '../../../../../../main/webapp/app/entities/user-access/user-access-detail.component';
import { UserAccessService } from '../../../../../../main/webapp/app/entities/user-access/user-access.service';
import { UserAccess } from '../../../../../../main/webapp/app/entities/user-access/user-access.model';

describe('Component Tests', () => {

    describe('UserAccess Management Detail Component', () => {
        let comp: UserAccessDetailComponent;
        let fixture: ComponentFixture<UserAccessDetailComponent>;
        let service: UserAccessService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [UserAccessDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserAccessService,
                    JhiEventManager
                ]
            }).overrideTemplate(UserAccessDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAccessDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAccessService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserAccess(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userAccess).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
