import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FieldDefinition } from './field-definition.model';
import { FieldDefinitionPopupService } from './field-definition-popup.service';
import { FieldDefinitionService } from './field-definition.service';
import { DefinitionConfig, DefinitionConfigService } from '../definition-config';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-field-definition-dialog',
    templateUrl: './field-definition-dialog.component.html'
})
export class FieldDefinitionDialogComponent implements OnInit {

    fieldDefinition: FieldDefinition;
    authorities: any[];
    isSaving: boolean;

    definitionconfigs: DefinitionConfig[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private fieldDefinitionService: FieldDefinitionService,
        private definitionConfigService: DefinitionConfigService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.definitionConfigService.query()
            .subscribe((res: ResponseWrapper) => { this.definitionconfigs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fieldDefinition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fieldDefinitionService.update(this.fieldDefinition), false);
        } else {
            this.subscribeToSaveResponse(
                this.fieldDefinitionService.create(this.fieldDefinition), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<FieldDefinition>, isCreated: boolean) {
        result.subscribe((res: FieldDefinition) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FieldDefinition, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Field Definition is created with identifier ${result.id}`
            : `A Field Definition is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'fieldDefinitionListModification', content: 'OK'});
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

    trackDefinitionConfigById(index: number, item: DefinitionConfig) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-field-definition-popup',
    template: ''
})
export class FieldDefinitionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fieldDefinitionPopupService: FieldDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.fieldDefinitionPopupService
                    .open(FieldDefinitionDialogComponent, params['id']);
            } else {
                this.modalRef = this.fieldDefinitionPopupService
                    .open(FieldDefinitionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
