import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RequestAttachment } from './request-attachment.model';
import { RequestAttachmentService } from './request-attachment.service';

@Component({
    selector: 'jhi-request-attachment-detail',
    templateUrl: './request-attachment-detail.component.html'
})
export class RequestAttachmentDetailComponent implements OnInit, OnDestroy {

    requestAttachment: RequestAttachment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private requestAttachmentService: RequestAttachmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRequestAttachments();
    }

    load(id) {
        this.requestAttachmentService.find(id).subscribe((requestAttachment) => {
            this.requestAttachment = requestAttachment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRequestAttachments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'requestAttachmentListModification',
            (response) => this.load(this.requestAttachment.id)
        );
    }
}
