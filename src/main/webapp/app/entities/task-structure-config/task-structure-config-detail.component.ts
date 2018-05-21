import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { TaskStructureConfig } from './task-structure-config.model';
import { TaskStructureConfigService } from './task-structure-config.service';

@Component({
    selector: 'jhi-task-structure-config-detail',
    templateUrl: './task-structure-config-detail.component.html'
})
export class TaskStructureConfigDetailComponent implements OnInit, OnDestroy {

    taskStructureConfig: TaskStructureConfig;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private taskStructureConfigService: TaskStructureConfigService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTaskStructureConfigs();
    }

    load(id) {
        this.taskStructureConfigService.find(id).subscribe((taskStructureConfig) => {
            this.taskStructureConfig = taskStructureConfig;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTaskStructureConfigs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'taskStructureConfigListModification',
            (response) => this.load(this.taskStructureConfig.id)
        );
    }
}
