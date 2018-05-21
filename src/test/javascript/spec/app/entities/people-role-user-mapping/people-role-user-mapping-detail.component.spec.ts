import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PeopleRoleUserMappingDetailComponent } from '../../../../../../main/webapp/app/entities/people-role-user-mapping/people-role-user-mapping-detail.component';
import { PeopleRoleUserMappingService } from '../../../../../../main/webapp/app/entities/people-role-user-mapping/people-role-user-mapping.service';
import { PeopleRoleUserMapping } from '../../../../../../main/webapp/app/entities/people-role-user-mapping/people-role-user-mapping.model';

describe('Component Tests', () => {

    describe('PeopleRoleUserMapping Management Detail Component', () => {
        let comp: PeopleRoleUserMappingDetailComponent;
        let fixture: ComponentFixture<PeopleRoleUserMappingDetailComponent>;
        let service: PeopleRoleUserMappingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [PeopleRoleUserMappingDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PeopleRoleUserMappingService,
                    JhiEventManager
                ]
            }).overrideTemplate(PeopleRoleUserMappingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeopleRoleUserMappingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeopleRoleUserMappingService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PeopleRoleUserMapping(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.peopleRoleUserMapping).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
