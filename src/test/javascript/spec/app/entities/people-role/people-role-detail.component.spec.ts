import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PeopleRoleDetailComponent } from '../../../../../../main/webapp/app/entities/people-role/people-role-detail.component';
import { PeopleRoleService } from '../../../../../../main/webapp/app/entities/people-role/people-role.service';
import { PeopleRole } from '../../../../../../main/webapp/app/entities/people-role/people-role.model';

describe('Component Tests', () => {

    describe('PeopleRole Management Detail Component', () => {
        let comp: PeopleRoleDetailComponent;
        let fixture: ComponentFixture<PeopleRoleDetailComponent>;
        let service: PeopleRoleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [PeopleRoleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PeopleRoleService,
                    JhiEventManager
                ]
            }).overrideTemplate(PeopleRoleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeopleRoleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeopleRoleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PeopleRole(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.peopleRole).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
