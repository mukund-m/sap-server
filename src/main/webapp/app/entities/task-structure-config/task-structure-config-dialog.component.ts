import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TaskStructureConfig } from './task-structure-config.model';
import { TaskStructureConfigPopupService } from './task-structure-config-popup.service';
import { TaskStructureConfigService } from './task-structure-config.service';
import { RequestTypeDefConfig, RequestTypeDefConfigService } from '../request-type-def-config';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-task-structure-config-dialog',
    templateUrl: './task-structure-config-dialog.component.html'
})
export class TaskStructureConfigDialogComponent implements OnInit {

    taskStructureConfig: TaskStructureConfig;
    authorities: any[];
    isSaving: boolean;

    requesttypedefconfigs: RequestTypeDefConfig[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private taskStructureConfigService: TaskStructureConfigService,
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
        if (this.taskStructureConfig.id !== undefined) {
            this.subscribeToSaveResponse(
                this.taskStructureConfigService.update(this.taskStructureConfig), false);
        } else {
            this.subscribeToSaveResponse(
                this.taskStructureConfigService.create(this.taskStructureConfig), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<TaskStructureConfig>, isCreated: boolean) {
        result.subscribe((res: TaskStructureConfig) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TaskStructureConfig, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Task Structure Config is created with identifier ${result.id}`
            : `A Task Structure Config is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'taskStructureConfigListModification', content: 'OK'});
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
    selector: 'jhi-task-structure-config-popup',
    template: ''
})
export class TaskStructureConfigPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taskStructureConfigPopupService: TaskStructureConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.taskStructureConfigPopupService
                    .open(TaskStructureConfigDialogComponent, params['id']);
            } else {
                this.modalRef = this.taskStructureConfigPopupService
                    .open(TaskStructureConfigDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
