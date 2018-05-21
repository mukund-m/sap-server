import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TaskQuestionInstanceDetailComponent } from '../../../../../../main/webapp/app/entities/task-question-instance/task-question-instance-detail.component';
import { TaskQuestionInstanceService } from '../../../../../../main/webapp/app/entities/task-question-instance/task-question-instance.service';
import { TaskQuestionInstance } from '../../../../../../main/webapp/app/entities/task-question-instance/task-question-instance.model';

describe('Component Tests', () => {

    describe('TaskQuestionInstance Management Detail Component', () => {
        let comp: TaskQuestionInstanceDetailComponent;
        let fixture: ComponentFixture<TaskQuestionInstanceDetailComponent>;
        let service: TaskQuestionInstanceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [TaskQuestionInstanceDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TaskQuestionInstanceService,
                    JhiEventManager
                ]
            }).overrideTemplate(TaskQuestionInstanceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaskQuestionInstanceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaskQuestionInstanceService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TaskQuestionInstance(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.taskQuestionInstance).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
