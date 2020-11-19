/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AuxiliumTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProfileAccessDetailComponent } from '../../../../../../main/webapp/app/entities/profile-access/profile-access-detail.component';
import { ProfileAccessService } from '../../../../../../main/webapp/app/entities/profile-access/profile-access.service';
import { ProfileAccess } from '../../../../../../main/webapp/app/entities/profile-access/profile-access.model';

describe('Component Tests', () => {

    describe('ProfileAccess Management Detail Component', () => {
        let comp: ProfileAccessDetailComponent;
        let fixture: ComponentFixture<ProfileAccessDetailComponent>;
        let service: ProfileAccessService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AuxiliumTestModule],
                declarations: [ProfileAccessDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProfileAccessService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProfileAccessDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfileAccessDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfileAccessService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProfileAccess(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.profileAccess).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
