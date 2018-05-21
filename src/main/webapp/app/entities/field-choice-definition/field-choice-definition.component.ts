import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { FieldChoiceDefinition } from './field-choice-definition.model';
import { FieldChoiceDefinitionService } from './field-choice-definition.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-field-choice-definition',
    templateUrl: './field-choice-definition.component.html'
})
export class FieldChoiceDefinitionComponent implements OnInit, OnDestroy {
fieldChoiceDefinitions: FieldChoiceDefinition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fieldChoiceDefinitionService: FieldChoiceDefinitionService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.fieldChoiceDefinitionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.fieldChoiceDefinitions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFieldChoiceDefinitions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FieldChoiceDefinition) {
        return item.id;
    }
    registerChangeInFieldChoiceDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe('fieldChoiceDefinitionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
