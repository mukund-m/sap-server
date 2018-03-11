import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { FieldOptionDefinition } from './field-option-definition.model';
import { FieldOptionDefinitionService } from './field-option-definition.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-field-option-definition',
    templateUrl: './field-option-definition.component.html'
})
export class FieldOptionDefinitionComponent implements OnInit, OnDestroy {
fieldOptionDefinitions: FieldOptionDefinition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fieldOptionDefinitionService: FieldOptionDefinitionService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.fieldOptionDefinitionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.fieldOptionDefinitions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFieldOptionDefinitions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FieldOptionDefinition) {
        return item.id;
    }
    registerChangeInFieldOptionDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe('fieldOptionDefinitionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
