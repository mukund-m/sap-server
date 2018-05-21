import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FieldChoiceDefinition } from './field-choice-definition.model';
import { FieldChoiceDefinitionPopupService } from './field-choice-definition-popup.service';
import { FieldChoiceDefinitionService } from './field-choice-definition.service';
import { FieldDefinition, FieldDefinitionService } from '../field-definition';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-field-choice-definition-dialog',
    templateUrl: './field-choice-definition-dialog.component.html'
})
export class FieldChoiceDefinitionDialogComponent implements OnInit {

    fieldChoiceDefinition: FieldChoiceDefinition;
    authorities: any[];
    isSaving: boolean;

    fielddefinitions: FieldDefinition[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private fieldChoiceDefinitionService: FieldChoiceDefinitionService,
        private fieldDefinitionService: FieldDefinitionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.fieldDefinitionService.query()
            .subscribe((res: ResponseWrapper) => { this.fielddefinitions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fieldChoiceDefinition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fieldChoiceDefinitionService.update(this.fieldChoiceDefinition), false);
        } else {
            this.subscribeToSaveResponse(
                this.fieldChoiceDefinitionService.create(this.fieldChoiceDefinition), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<FieldChoiceDefinition>, isCreated: boolean) {
        result.subscribe((res: FieldChoiceDefinition) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FieldChoiceDefinition, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Field Choice Definition is created with identifier ${result.id}`
            : `A Field Choice Definition is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'fieldChoiceDefinitionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackFieldDefinitionById(index: number, item: FieldDefinition) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-field-choice-definition-popup',
    template: ''
})
export class FieldChoiceDefinitionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fieldChoiceDefinitionPopupService: FieldChoiceDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.fieldChoiceDefinitionPopupService
                    .open(FieldChoiceDefinitionDialogComponent, params['id']);
            } else {
                this.modalRef = this.fieldChoiceDefinitionPopupService
                    .open(FieldChoiceDefinitionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
