import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AttachmentType } from './attachment-type.model';
import { AttachmentTypePopupService } from './attachment-type-popup.service';
import { AttachmentTypeService } from './attachment-type.service';
import { RequestTypeDefConfig, RequestTypeDefConfigService } from '../request-type-def-config';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-attachment-type-dialog',
    templateUrl: './attachment-type-dialog.component.html'
})
export class AttachmentTypeDialogComponent implements OnInit {

    attachmentType: AttachmentType;
    authorities: any[];
    isSaving: boolean;

    requesttypedefconfigs: RequestTypeDefConfig[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private attachmentTypeService: AttachmentTypeService,
        private requestTypeDefConfigService: RequestTypeDefConfigService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.requestTypeDefConfigService.query()
            .subscribe((res: ResponseWrapper) => { this.requesttypedefconfigs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.attachmentType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.attachmentTypeService.update(this.attachmentType), false);
        } else {
            this.subscribeToSaveResponse(
                this.attachmentTypeService.create(this.attachmentType), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<AttachmentType>, isCreated: boolean) {
        result.subscribe((res: AttachmentType) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AttachmentType, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Attachment Type is created with identifier ${result.id}`
            : `A Attachment Type is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'attachmentTypeListModification', content: 'OK'});
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

    trackRequestTypeDefConfigById(index: number, item: RequestTypeDefConfig) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-attachment-type-popup',
    template: ''
})
export class AttachmentTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attachmentTypePopupService: AttachmentTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.attachmentTypePopupService
                    .open(AttachmentTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.attachmentTypePopupService
                    .open(AttachmentTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
