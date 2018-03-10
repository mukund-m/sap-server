import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ReuestDefinition } from './reuest-definition.model';
import { ReuestDefinitionService } from './reuest-definition.service';

@Injectable()
export class ReuestDefinitionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private reuestDefinitionService: ReuestDefinitionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.reuestDefinitionService.find(id).subscribe((reuestDefinition) => {
                this.reuestDefinitionModalRef(component, reuestDefinition);
            });
        } else {
            return this.reuestDefinitionModalRef(component, new ReuestDefinition());
        }
    }

    reuestDefinitionModalRef(component: Component, reuestDefinition: ReuestDefinition): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reuestDefinition = reuestDefinition;
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
