import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TaskQuestionInstance } from './task-question-instance.model';
import { TaskQuestionInstancePopupService } from './task-question-instance-popup.service';
import { TaskQuestionInstanceService } from './task-question-instance.service';
import { Request, RequestService } from '../request';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-task-question-instance-dialog',
    templateUrl: './task-question-instance-dialog.component.html'
})
export class TaskQuestionInstanceDialogComponent implements OnInit {

    taskQuestionInstance: TaskQuestionInstance;
    authorities: any[];
    isSaving: boolean;

    requests: Request[];
    dueDateDp: any;
    notifiedDateDp: any;
    completedDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private taskQuestionInstanceService: TaskQuestionInstanceService,
        private requestService: RequestService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.requestService.query()
            .subscribe((res: ResponseWrapper) => { this.requests = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.taskQuestionInstance.id !== undefined) {
            this.subscribeToSaveResponse(
                this.taskQuestionInstanceService.update(this.taskQuestionInstance), false);
        } else {
            this.subscribeToSaveResponse(
                this.taskQuestionInstanceService.create(this.taskQuestionInstance), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<TaskQuestionInstance>, isCreated: boolean) {
        result.subscribe((res: TaskQuestionInstance) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TaskQuestionInstance, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Task Question Instance is created with identifier ${result.id}`
            : `A Task Question Instance is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'taskQuestionInstanceListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-task-question-instance-popup',
    template: ''
})
export class TaskQuestionInstancePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taskQuestionInstancePopupService: TaskQuestionInstancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.taskQuestionInstancePopupService
                    .open(TaskQuestionInstanceDialogComponent, params['id']);
            } else {
                this.modalRef = this.taskQuestionInstancePopupService
                    .open(TaskQuestionInstanceDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
