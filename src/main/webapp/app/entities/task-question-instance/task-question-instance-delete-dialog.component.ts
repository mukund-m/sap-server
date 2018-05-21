import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { TaskQuestionInstance } from './task-question-instance.model';
import { TaskQuestionInstancePopupService } from './task-question-instance-popup.service';
import { TaskQuestionInstanceService } from './task-question-instance.service';

@Component({
    selector: 'jhi-task-question-instance-delete-dialog',
    templateUrl: './task-question-instance-delete-dialog.component.html'
})
export class TaskQuestionInstanceDeleteDialogComponent {

    taskQuestionInstance: TaskQuestionInstance;

    constructor(
        private taskQuestionInstanceService: TaskQuestionInstanceService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.taskQuestionInstanceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'taskQuestionInstanceListModification',
                content: 'Deleted an taskQuestionInstance'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Task Question Instance is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-task-question-instance-delete-popup',
    template: ''
})
export class TaskQuestionInstanceDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taskQuestionInstancePopupService: TaskQuestionInstancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.taskQuestionInstancePopupService
                .open(TaskQuestionInstanceDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
