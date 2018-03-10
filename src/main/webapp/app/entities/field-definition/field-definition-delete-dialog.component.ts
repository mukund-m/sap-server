import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { FieldDefinition } from './field-definition.model';
import { FieldDefinitionPopupService } from './field-definition-popup.service';
import { FieldDefinitionService } from './field-definition.service';

@Component({
    selector: 'jhi-field-definition-delete-dialog',
    templateUrl: './field-definition-delete-dialog.component.html'
})
export class FieldDefinitionDeleteDialogComponent {

    fieldDefinition: FieldDefinition;

    constructor(
        private fieldDefinitionService: FieldDefinitionService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fieldDefinitionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fieldDefinitionListModification',
                content: 'Deleted an fieldDefinition'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Field Definition is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-field-definition-delete-popup',
    template: ''
})
export class FieldDefinitionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fieldDefinitionPopupService: FieldDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.fieldDefinitionPopupService
                .open(FieldDefinitionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
