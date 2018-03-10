import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Request } from './request.model';
import { RequestService } from './request.service';

@Injectable()
export class RequestPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private requestService: RequestService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.requestService.find(id).subscribe((request) => {
                if (request.startDate) {
                    request.startDate = {
                        year: request.startDate.getFullYear(),
                        month: request.startDate.getMonth() + 1,
                        day: request.startDate.getDate()
                    };
                }
                if (request.createdOn) {
                    request.createdOn = {
                        year: request.createdOn.getFullYear(),
                        month: request.createdOn.getMonth() + 1,
                        day: request.createdOn.getDate()
                    };
                }
                if (request.modifiedOn) {
                    request.modifiedOn = {
                        year: request.modifiedOn.getFullYear(),
                        month: request.modifiedOn.getMonth() + 1,
                        day: request.modifiedOn.getDate()
                    };
                }
                this.requestModalRef(component, request);
            });
        } else {
            return this.requestModalRef(component, new Request());
        }
    }

    requestModalRef(component: Component, request: Request): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.request = request;
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
