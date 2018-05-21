import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RefCodeDetails } from './ref-code-details.model';
import { RefCodeDetailsService } from './ref-code-details.service';

@Injectable()
export class RefCodeDetailsPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private refCodeDetailsService: RefCodeDetailsService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.refCodeDetailsService.find(id).subscribe((refCodeDetails) => {
                this.refCodeDetailsModalRef(component, refCodeDetails);
            });
        } else {
            return this.refCodeDetailsModalRef(component, new RefCodeDetails());
        }
    }

    refCodeDetailsModalRef(component: Component, refCodeDetails: RefCodeDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.refCodeDetails = refCodeDetails;
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
