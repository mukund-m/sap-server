import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { RequestAttachment } from './request-attachment.model';
import { RequestAttachmentPopupService } from './request-attachment-popup.service';
import { RequestAttachmentService } from './request-attachment.service';

@Component({
    selector: 'jhi-request-attachment-delete-dialog',
    templateUrl: './request-attachment-delete-dialog.component.html'
})
export class RequestAttachmentDeleteDialogComponent {

    requestAttachment: RequestAttachment;

    constructor(
        private requestAttachmentService: RequestAttachmentService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requestAttachmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestAttachmentListModification',
                content: 'Deleted an requestAttachment'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Request Attachment is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-request-attachment-delete-popup',
    template: ''
})
export class RequestAttachmentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestAttachmentPopupService: RequestAttachmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.requestAttachmentPopupService
                .open(RequestAttachmentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
