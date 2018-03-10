import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RequestAttachment } from './request-attachment.model';
import { RequestAttachmentService } from './request-attachment.service';

@Injectable()
export class RequestAttachmentPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private requestAttachmentService: RequestAttachmentService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.requestAttachmentService.find(id).subscribe((requestAttachment) => {
                if (requestAttachment.uploadedOn) {
                    requestAttachment.uploadedOn = {
                        year: requestAttachment.uploadedOn.getFullYear(),
                        month: requestAttachment.uploadedOn.getMonth() + 1,
                        day: requestAttachment.uploadedOn.getDate()
                    };
                }
                this.requestAttachmentModalRef(component, requestAttachment);
            });
        } else {
            return this.requestAttachmentModalRef(component, new RequestAttachment());
        }
    }

    requestAttachmentModalRef(component: Component, requestAttachment: RequestAttachment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.requestAttachment = requestAttachment;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
