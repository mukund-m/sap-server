import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { RefCodeDetails } from './ref-code-details.model';
import { RefCodeDetailsService } from './ref-code-details.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ref-code-details',
    templateUrl: './ref-code-details.component.html'
})
export class RefCodeDetailsComponent implements OnInit, OnDestroy {
refCodeDetails: RefCodeDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private refCodeDetailsService: RefCodeDetailsService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.refCodeDetailsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.refCodeDetails = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRefCodeDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RefCodeDetails) {
        return item.id;
    }
    registerChangeInRefCodeDetails() {
        this.eventSubscriber = this.eventManager.subscribe('refCodeDetailsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
