import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { FieldOptionDefinition } from './field-option-definition.model';
import { FieldOptionDefinitionPopupService } from './field-option-definition-popup.service';
import { FieldOptionDefinitionService } from './field-option-definition.service';

@Component({
    selector: 'jhi-field-option-definition-delete-dialog',
    templateUrl: './field-option-definition-delete-dialog.component.html'
})
export class FieldOptionDefinitionDeleteDialogComponent {

    fieldOptionDefinition: FieldOptionDefinition;

    constructor(
        private fieldOptionDefinitionService: FieldOptionDefinitionService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fieldOptionDefinitionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fieldOptionDefinitionListModification',
                content: 'Deleted an fieldOptionDefinition'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Field Option Definition is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-field-option-definition-delete-popup',
    template: ''
})
export class FieldOptionDefinitionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fieldOptionDefinitionPopupService: FieldOptionDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.fieldOptionDefinitionPopupService
                .open(FieldOptionDefinitionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
