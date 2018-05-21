import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TaskStructureConfigDetailComponent } from '../../../../../../main/webapp/app/entities/task-structure-config/task-structure-config-detail.component';
import { TaskStructureConfigService } from '../../../../../../main/webapp/app/entities/task-structure-config/task-structure-config.service';
import { TaskStructureConfig } from '../../../../../../main/webapp/app/entities/task-structure-config/task-structure-config.model';

describe('Component Tests', () => {

    describe('TaskStructureConfig Management Detail Component', () => {
        let comp: TaskStructureConfigDetailComponent;
        let fixture: ComponentFixture<TaskStructureConfigDetailComponent>;
        let service: TaskStructureConfigService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [TaskStructureConfigDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TaskStructureConfigService,
                    JhiEventManager
                ]
            }).overrideTemplate(TaskStructureConfigDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaskStructureConfigDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaskStructureConfigService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TaskStructureConfig(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.taskStructureConfig).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
