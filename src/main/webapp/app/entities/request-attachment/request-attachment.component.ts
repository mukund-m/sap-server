import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { RequestAttachment } from './request-attachment.model';
import { RequestAttachmentService } from './request-attachment.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-request-attachment',
    templateUrl: './request-attachment.component.html'
})
export class RequestAttachmentComponent implements OnInit, OnDestroy {
requestAttachments: RequestAttachment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private requestAttachmentService: RequestAttachmentService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.requestAttachmentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.requestAttachments = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRequestAttachments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RequestAttachment) {
        return item.id;
    }
    registerChangeInRequestAttachments() {
        this.eventSubscriber = this.eventManager.subscribe('requestAttachmentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
