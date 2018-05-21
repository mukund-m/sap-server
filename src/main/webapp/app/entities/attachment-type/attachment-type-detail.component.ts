import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { AttachmentType } from './attachment-type.model';
import { AttachmentTypeService } from './attachment-type.service';

@Component({
    selector: 'jhi-attachment-type-detail',
    templateUrl: './attachment-type-detail.component.html'
})
export class AttachmentTypeDetailComponent implements OnInit, OnDestroy {

    attachmentType: AttachmentType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private attachmentTypeService: AttachmentTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAttachmentTypes();
    }

    load(id) {
        this.attachmentTypeService.find(id).subscribe((attachmentType) => {
            this.attachmentType = attachmentType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAttachmentTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'attachmentTypeListModification',
            (response) => this.load(this.attachmentType.id)
        );
    }
}
