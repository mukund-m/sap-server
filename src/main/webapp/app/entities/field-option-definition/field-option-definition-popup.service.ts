import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FieldOptionDefinition } from './field-option-definition.model';
import { FieldOptionDefinitionService } from './field-option-definition.service';

@Injectable()
export class FieldOptionDefinitionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private fieldOptionDefinitionService: FieldOptionDefinitionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.fieldOptionDefinitionService.find(id).subscribe((fieldOptionDefinition) => {
                this.fieldOptionDefinitionModalRef(component, fieldOptionDefinition);
            });
        } else {
            return this.fieldOptionDefinitionModalRef(component, new FieldOptionDefinition());
        }
    }

    fieldOptionDefinitionModalRef(component: Component, fieldOptionDefinition: FieldOptionDefinition): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fieldOptionDefinition = fieldOptionDefinition;
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
