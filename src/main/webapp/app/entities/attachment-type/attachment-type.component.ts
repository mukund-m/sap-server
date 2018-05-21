import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { AttachmentType } from './attachment-type.model';
import { AttachmentTypeService } from './attachment-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-attachment-type',
    templateUrl: './attachment-type.component.html'
})
export class AttachmentTypeComponent implements OnInit, OnDestroy {
attachmentTypes: AttachmentType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private attachmentTypeService: AttachmentTypeService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.attachmentTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.attachmentTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAttachmentTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AttachmentType) {
        return item.id;
    }
    registerChangeInAttachmentTypes() {
        this.eventSubscriber = this.eventManager.subscribe('attachmentTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
