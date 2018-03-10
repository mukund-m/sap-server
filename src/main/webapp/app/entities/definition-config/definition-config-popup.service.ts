import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DefinitionConfig } from './definition-config.model';
import { DefinitionConfigService } from './definition-config.service';

@Injectable()
export class DefinitionConfigPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private definitionConfigService: DefinitionConfigService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.definitionConfigService.find(id).subscribe((definitionConfig) => {
                this.definitionConfigModalRef(component, definitionConfig);
            });
        } else {
            return this.definitionConfigModalRef(component, new DefinitionConfig());
        }
    }

    definitionConfigModalRef(component: Component, definitionConfig: DefinitionConfig): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.definitionConfig = definitionConfig;
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
