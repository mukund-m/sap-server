import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestTypeDefConfig } from './request-type-def-config.model';
import { RequestTypeDefConfigPopupService } from './request-type-def-config-popup.service';
import { RequestTypeDefConfigService } from './request-type-def-config.service';
import { DefinitionConfig, DefinitionConfigService } from '../definition-config';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-request-type-def-config-dialog',
    templateUrl: './request-type-def-config-dialog.component.html'
})
export class RequestTypeDefConfigDialogComponent implements OnInit {

    requestTypeDefConfig: RequestTypeDefConfig;
    authorities: any[];
    isSaving: boolean;

    definitions: DefinitionConfig[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private requestTypeDefConfigService: RequestTypeDefConfigService,
        private definitionConfigService: DefinitionConfigService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.definitionConfigService
            .query({filter: 'reqtype-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.requestTypeDefConfig.definition || !this.requestTypeDefConfig.definition.id) {
                    this.definitions = res.json;
                } else {
                    this.definitionConfigService
                        .find(this.requestTypeDefConfig.definition.id)
                        .subscribe((subRes: DefinitionConfig) => {
                            this.definitions = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.requestTypeDefConfig.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestTypeDefConfigService.update(this.requestTypeDefConfig), false);
        } else {
            this.subscribeToSaveResponse(
                this.requestTypeDefConfigService.create(this.requestTypeDefConfig), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<RequestTypeDefConfig>, isCreated: boolean) {
        result.subscribe((res: RequestTypeDefConfig) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RequestTypeDefConfig, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Request Type Def Config is created with identifier ${result.id}`
            : `A Request Type Def Config is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'requestTypeDefConfigListModification', content: 'OK'});
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
    selector: 'jhi-request-type-def-config-popup',
    template: ''
})
export class RequestTypeDefConfigPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestTypeDefConfigPopupService: RequestTypeDefConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.requestTypeDefConfigPopupService
                    .open(RequestTypeDefConfigDialogComponent, params['id']);
            } else {
                this.modalRef = this.requestTypeDefConfigPopupService
                    .open(RequestTypeDefConfigDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
