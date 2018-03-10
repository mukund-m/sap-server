import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReuestDefinitionDetailComponent } from '../../../../../../main/webapp/app/entities/reuest-definition/reuest-definition-detail.component';
import { ReuestDefinitionService } from '../../../../../../main/webapp/app/entities/reuest-definition/reuest-definition.service';
import { ReuestDefinition } from '../../../../../../main/webapp/app/entities/reuest-definition/reuest-definition.model';

describe('Component Tests', () => {

    describe('ReuestDefinition Management Detail Component', () => {
        let comp: ReuestDefinitionDetailComponent;
        let fixture: ComponentFixture<ReuestDefinitionDetailComponent>;
        let service: ReuestDefinitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [ReuestDefinitionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReuestDefinitionService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReuestDefinitionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReuestDefinitionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReuestDefinitionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ReuestDefinition(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reuestDefinition).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
