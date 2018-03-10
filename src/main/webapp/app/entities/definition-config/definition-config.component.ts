import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { DefinitionConfig } from './definition-config.model';
import { DefinitionConfigService } from './definition-config.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-definition-config',
    templateUrl: './definition-config.component.html'
})
export class DefinitionConfigComponent implements OnInit, OnDestroy {
definitionConfigs: DefinitionConfig[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private definitionConfigService: DefinitionConfigService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.definitionConfigService.query().subscribe(
            (res: ResponseWrapper) => {
                this.definitionConfigs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDefinitionConfigs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DefinitionConfig) {
        return item.id;
    }
    registerChangeInDefinitionConfigs() {
        this.eventSubscriber = this.eventManager.subscribe('definitionConfigListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
