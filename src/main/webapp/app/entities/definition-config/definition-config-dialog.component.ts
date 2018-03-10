import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DefinitionConfig } from './definition-config.model';
import { DefinitionConfigPopupService } from './definition-config-popup.service';
import { DefinitionConfigService } from './definition-config.service';
import { RequestTypeDefConfig, RequestTypeDefConfigService } from '../request-type-def-config';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-definition-config-dialog',
    templateUrl: './definition-config-dialog.component.html'
})
export class DefinitionConfigDialogComponent implements OnInit {

    definitionConfig: DefinitionConfig;
    authorities: any[];
    isSaving: boolean;

    requesttypedefconfigs: RequestTypeDefConfig[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private definitionConfigService: DefinitionConfigService,
        private requestTypeDefConfigService: RequestTypeDefConfigService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.requestTypeDefConfigService.query()
            .subscribe((res: ResponseWrapper) => { this.requesttypedefconfigs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.definitionConfig.id !== undefined) {
            this.subscribeToSaveResponse(
                this.definitionConfigService.update(this.definitionConfig), false);
        } else {
            this.subscribeToSaveResponse(
                this.definitionConfigService.create(this.definitionConfig), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<DefinitionConfig>, isCreated: boolean) {
        result.subscribe((res: DefinitionConfig) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DefinitionConfig, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Definition Config is created with identifier ${result.id}`
            : `A Definition Config is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'definitionConfigListModification', content: 'OK'});
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

    trackRequestTypeDefConfigById(index: number, item: RequestTypeDefConfig) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-definition-config-popup',
    template: ''
})
export class DefinitionConfigPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private definitionConfigPopupService: DefinitionConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.definitionConfigPopupService
                    .open(DefinitionConfigDialogComponent, params['id']);
            } else {
                this.modalRef = this.definitionConfigPopupService
                    .open(DefinitionConfigDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
