import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FieldDefinition } from './field-definition.model';
import { FieldDefinitionService } from './field-definition.service';

@Injectable()
export class FieldDefinitionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private fieldDefinitionService: FieldDefinitionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.fieldDefinitionService.find(id).subscribe((fieldDefinition) => {
                this.fieldDefinitionModalRef(component, fieldDefinition);
            });
        } else {
            return this.fieldDefinitionModalRef(component, new FieldDefinition());
        }
    }

    fieldDefinitionModalRef(component: Component, fieldDefinition: FieldDefinition): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fieldDefinition = fieldDefinition;
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
