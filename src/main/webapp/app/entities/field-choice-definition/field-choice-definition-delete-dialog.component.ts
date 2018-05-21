import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { FieldChoiceDefinition } from './field-choice-definition.model';
import { FieldChoiceDefinitionPopupService } from './field-choice-definition-popup.service';
import { FieldChoiceDefinitionService } from './field-choice-definition.service';

@Component({
    selector: 'jhi-field-choice-definition-delete-dialog',
    templateUrl: './field-choice-definition-delete-dialog.component.html'
})
export class FieldChoiceDefinitionDeleteDialogComponent {

    fieldChoiceDefinition: FieldChoiceDefinition;

    constructor(
        private fieldChoiceDefinitionService: FieldChoiceDefinitionService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fieldChoiceDefinitionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fieldChoiceDefinitionListModification',
                content: 'Deleted an fieldChoiceDefinition'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Field Choice Definition is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-field-choice-definition-delete-popup',
    template: ''
})
export class FieldChoiceDefinitionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fieldChoiceDefinitionPopupService: FieldChoiceDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.fieldChoiceDefinitionPopupService
                .open(FieldChoiceDefinitionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
