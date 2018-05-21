import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { TaskStructureConfig } from './task-structure-config.model';
import { TaskStructureConfigPopupService } from './task-structure-config-popup.service';
import { TaskStructureConfigService } from './task-structure-config.service';

@Component({
    selector: 'jhi-task-structure-config-delete-dialog',
    templateUrl: './task-structure-config-delete-dialog.component.html'
})
export class TaskStructureConfigDeleteDialogComponent {

    taskStructureConfig: TaskStructureConfig;

    constructor(
        private taskStructureConfigService: TaskStructureConfigService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.taskStructureConfigService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'taskStructureConfigListModification',
                content: 'Deleted an taskStructureConfig'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Task Structure Config is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-task-structure-config-delete-popup',
    template: ''
})
export class TaskStructureConfigDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taskStructureConfigPopupService: TaskStructureConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.taskStructureConfigPopupService
                .open(TaskStructureConfigDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
