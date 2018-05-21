import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AttachmentType } from './attachment-type.model';
import { AttachmentTypeService } from './attachment-type.service';

@Injectable()
export class AttachmentTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private attachmentTypeService: AttachmentTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.attachmentTypeService.find(id).subscribe((attachmentType) => {
                this.attachmentTypeModalRef(component, attachmentType);
            });
        } else {
            return this.attachmentTypeModalRef(component, new AttachmentType());
        }
    }

    attachmentTypeModalRef(component: Component, attachmentType: AttachmentType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.attachmentType = attachmentType;
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
