import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { FieldDefinition } from './field-definition.model';
import { FieldDefinitionService } from './field-definition.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-field-definition',
    templateUrl: './field-definition.component.html'
})
export class FieldDefinitionComponent implements OnInit, OnDestroy {
fieldDefinitions: FieldDefinition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fieldDefinitionService: FieldDefinitionService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.fieldDefinitionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.fieldDefinitions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFieldDefinitions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FieldDefinition) {
        return item.id;
    }
    registerChangeInFieldDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe('fieldDefinitionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
