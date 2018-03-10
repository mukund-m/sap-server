import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RequestTypeDefConfig } from './request-type-def-config.model';
import { RequestTypeDefConfigService } from './request-type-def-config.service';

@Injectable()
export class RequestTypeDefConfigPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private requestTypeDefConfigService: RequestTypeDefConfigService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.requestTypeDefConfigService.find(id).subscribe((requestTypeDefConfig) => {
                this.requestTypeDefConfigModalRef(component, requestTypeDefConfig);
            });
        } else {
            return this.requestTypeDefConfigModalRef(component, new RequestTypeDefConfig());
        }
    }

    requestTypeDefConfigModalRef(component: Component, requestTypeDefConfig: RequestTypeDefConfig): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.requestTypeDefConfig = requestTypeDefConfig;
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
