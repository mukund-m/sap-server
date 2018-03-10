import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ReuestDefinition } from './reuest-definition.model';
import { ReuestDefinitionPopupService } from './reuest-definition-popup.service';
import { ReuestDefinitionService } from './reuest-definition.service';
import { Request, RequestService } from '../request';
import { FieldDefinition, FieldDefinitionService } from '../field-definition';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-reuest-definition-dialog',
    templateUrl: './reuest-definition-dialog.component.html'
})
export class ReuestDefinitionDialogComponent implements OnInit {

    reuestDefinition: ReuestDefinition;
    authorities: any[];
    isSaving: boolean;

    requests: Request[];

    fielddefinitions: FieldDefinition[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private reuestDefinitionService: ReuestDefinitionService,
        private requestService: RequestService,
        private fieldDefinitionService: FieldDefinitionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.requestService.query()
            .subscribe((res: ResponseWrapper) => { this.requests = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.fieldDefinitionService.query()
            .subscribe((res: ResponseWrapper) => { this.fielddefinitions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reuestDefinition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reuestDefinitionService.update(this.reuestDefinition), false);
        } else {
            this.subscribeToSaveResponse(
                this.reuestDefinitionService.create(this.reuestDefinition), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<ReuestDefinition>, isCreated: boolean) {
        result.subscribe((res: ReuestDefinition) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ReuestDefinition, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Reuest Definition is created with identifier ${result.id}`
            : `A Reuest Definition is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'reuestDefinitionListModification', content: 'OK'});
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

    trackRequestById(index: number, item: Request) {
        return item.id;
    }

    trackFieldDefinitionById(index: number, item: FieldDefinition) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-reuest-definition-popup',
    template: ''
})
export class ReuestDefinitionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reuestDefinitionPopupService: ReuestDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.reuestDefinitionPopupService
                    .open(ReuestDefinitionDialogComponent, params['id']);
            } else {
                this.modalRef = this.reuestDefinitionPopupService
                    .open(ReuestDefinitionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
