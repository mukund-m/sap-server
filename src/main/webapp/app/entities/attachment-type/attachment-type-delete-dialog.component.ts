import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { AttachmentType } from './attachment-type.model';
import { AttachmentTypePopupService } from './attachment-type-popup.service';
import { AttachmentTypeService } from './attachment-type.service';

@Component({
    selector: 'jhi-attachment-type-delete-dialog',
    templateUrl: './attachment-type-delete-dialog.component.html'
})
export class AttachmentTypeDeleteDialogComponent {

    attachmentType: AttachmentType;

    constructor(
        private attachmentTypeService: AttachmentTypeService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.attachmentTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'attachmentTypeListModification',
                content: 'Deleted an attachmentType'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Attachment Type is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-attachment-type-delete-popup',
    template: ''
})
export class AttachmentTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attachmentTypePopupService: AttachmentTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.attachmentTypePopupService
                .open(AttachmentTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
