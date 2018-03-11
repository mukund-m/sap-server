import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FieldOptionDefinition } from './field-option-definition.model';
import { FieldOptionDefinitionPopupService } from './field-option-definition-popup.service';
import { FieldOptionDefinitionService } from './field-option-definition.service';
import { FieldDefinition, FieldDefinitionService } from '../field-definition';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-field-option-definition-dialog',
    templateUrl: './field-option-definition-dialog.component.html'
})
export class FieldOptionDefinitionDialogComponent implements OnInit {

    fieldOptionDefinition: FieldOptionDefinition;
    authorities: any[];
    isSaving: boolean;

    fielddefinitions: FieldDefinition[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private fieldOptionDefinitionService: FieldOptionDefinitionService,
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
        if (this.fieldOptionDefinition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fieldOptionDefinitionService.update(this.fieldOptionDefinition), false);
        } else {
            this.subscribeToSaveResponse(
                this.fieldOptionDefinitionService.create(this.fieldOptionDefinition), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<FieldOptionDefinition>, isCreated: boolean) {
        result.subscribe((res: FieldOptionDefinition) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FieldOptionDefinition, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Field Option Definition is created with identifier ${result.id}`
            : `A Field Option Definition is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'fieldOptionDefinitionListModification', content: 'OK'});
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
    selector: 'jhi-field-option-definition-popup',
    template: ''
})
export class FieldOptionDefinitionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fieldOptionDefinitionPopupService: FieldOptionDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.fieldOptionDefinitionPopupService
                    .open(FieldOptionDefinitionDialogComponent, params['id']);
            } else {
                this.modalRef = this.fieldOptionDefinitionPopupService
                    .open(FieldOptionDefinitionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
