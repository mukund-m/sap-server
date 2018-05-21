import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { TaskQuestionInstance } from './task-question-instance.model';
import { TaskQuestionInstanceService } from './task-question-instance.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-task-question-instance',
    templateUrl: './task-question-instance.component.html'
})
export class TaskQuestionInstanceComponent implements OnInit, OnDestroy {
taskQuestionInstances: TaskQuestionInstance[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private taskQuestionInstanceService: TaskQuestionInstanceService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.taskQuestionInstanceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.taskQuestionInstances = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTaskQuestionInstances();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TaskQuestionInstance) {
        return item.id;
    }
    registerChangeInTaskQuestionInstances() {
        this.eventSubscriber = this.eventManager.subscribe('taskQuestionInstanceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
