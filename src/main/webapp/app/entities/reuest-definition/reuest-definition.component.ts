import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { ReuestDefinition } from './reuest-definition.model';
import { ReuestDefinitionService } from './reuest-definition.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-reuest-definition',
    templateUrl: './reuest-definition.component.html'
})
export class ReuestDefinitionComponent implements OnInit, OnDestroy {
reuestDefinitions: ReuestDefinition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private reuestDefinitionService: ReuestDefinitionService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.reuestDefinitionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.reuestDefinitions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInReuestDefinitions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ReuestDefinition) {
        return item.id;
    }
    registerChangeInReuestDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe('reuestDefinitionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
