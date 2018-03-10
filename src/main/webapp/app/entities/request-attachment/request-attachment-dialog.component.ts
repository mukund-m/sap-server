import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestAttachment } from './request-attachment.model';
import { RequestAttachmentPopupService } from './request-attachment-popup.service';
import { RequestAttachmentService } from './request-attachment.service';
import { Request, RequestService } from '../request';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-request-attachment-dialog',
    templateUrl: './request-attachment-dialog.component.html'
})
export class RequestAttachmentDialogComponent implements OnInit {

    requestAttachment: RequestAttachment;
    authorities: any[];
    isSaving: boolean;

    requests: Request[];
    uploadedOnDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private requestAttachmentService: RequestAttachmentService,
        private requestService: RequestService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.requestService.query()
            .subscribe((res: ResponseWrapper) => { this.requests = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.requestAttachment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestAttachmentService.update(this.requestAttachment), false);
        } else {
            this.subscribeToSaveResponse(
                this.requestAttachmentService.create(this.requestAttachment), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<RequestAttachment>, isCreated: boolean) {
        result.subscribe((res: RequestAttachment) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RequestAttachment, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Request Attachment is created with identifier ${result.id}`
            : `A Request Attachment is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'requestAttachmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackRequestById(index: number, item: Request) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-request-attachment-popup',
    template: ''
})
export class RequestAttachmentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestAttachmentPopupService: RequestAttachmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.requestAttachmentPopupService
                    .open(RequestAttachmentDialogComponent, params['id']);
            } else {
                this.modalRef = this.requestAttachmentPopupService
                    .open(RequestAttachmentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
