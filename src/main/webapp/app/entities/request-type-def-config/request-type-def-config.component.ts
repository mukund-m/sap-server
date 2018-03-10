import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { RequestTypeDefConfig } from './request-type-def-config.model';
import { RequestTypeDefConfigService } from './request-type-def-config.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-request-type-def-config',
    templateUrl: './request-type-def-config.component.html'
})
export class RequestTypeDefConfigComponent implements OnInit, OnDestroy {
requestTypeDefConfigs: RequestTypeDefConfig[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private requestTypeDefConfigService: RequestTypeDefConfigService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.requestTypeDefConfigService.query().subscribe(
            (res: ResponseWrapper) => {
                this.requestTypeDefConfigs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRequestTypeDefConfigs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RequestTypeDefConfig) {
        return item.id;
    }
    registerChangeInRequestTypeDefConfigs() {
        this.eventSubscriber = this.eventManager.subscribe('requestTypeDefConfigListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
