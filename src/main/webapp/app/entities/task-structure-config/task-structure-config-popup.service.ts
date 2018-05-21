import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TaskStructureConfig } from './task-structure-config.model';
import { TaskStructureConfigService } from './task-structure-config.service';

@Injectable()
export class TaskStructureConfigPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private taskStructureConfigService: TaskStructureConfigService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.taskStructureConfigService.find(id).subscribe((taskStructureConfig) => {
                this.taskStructureConfigModalRef(component, taskStructureConfig);
            });
        } else {
            return this.taskStructureConfigModalRef(component, new TaskStructureConfig());
        }
    }

    taskStructureConfigModalRef(component: Component, taskStructureConfig: TaskStructureConfig): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.taskStructureConfig = taskStructureConfig;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
