import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { TaskStructureConfig } from './task-structure-config.model';
import { TaskStructureConfigService } from './task-structure-config.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-task-structure-config',
    templateUrl: './task-structure-config.component.html'
})
export class TaskStructureConfigComponent implements OnInit, OnDestroy {
taskStructureConfigs: TaskStructureConfig[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private taskStructureConfigService: TaskStructureConfigService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.taskStructureConfigService.query().subscribe(
            (res: ResponseWrapper) => {
                this.taskStructureConfigs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTaskStructureConfigs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TaskStructureConfig) {
        return item.id;
    }
    registerChangeInTaskStructureConfigs() {
        this.eventSubscriber = this.eventManager.subscribe('taskStructureConfigListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
