import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { TaskQuestionInstance } from './task-question-instance.model';
import { TaskQuestionInstanceService } from './task-question-instance.service';

@Component({
    selector: 'jhi-task-question-instance-detail',
    templateUrl: './task-question-instance-detail.component.html'
})
export class TaskQuestionInstanceDetailComponent implements OnInit, OnDestroy {

    taskQuestionInstance: TaskQuestionInstance;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private taskQuestionInstanceService: TaskQuestionInstanceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTaskQuestionInstances();
    }

    load(id) {
        this.taskQuestionInstanceService.find(id).subscribe((taskQuestionInstance) => {
            this.taskQuestionInstance = taskQuestionInstance;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTaskQuestionInstances() {
        this.eventSubscriber = this.eventManager.subscribe(
            'taskQuestionInstanceListModification',
            (response) => this.load(this.taskQuestionInstance.id)
        );
    }
}
