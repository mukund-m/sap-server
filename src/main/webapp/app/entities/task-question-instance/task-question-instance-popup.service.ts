import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TaskQuestionInstance } from './task-question-instance.model';
import { TaskQuestionInstanceService } from './task-question-instance.service';

@Injectable()
export class TaskQuestionInstancePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private taskQuestionInstanceService: TaskQuestionInstanceService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.taskQuestionInstanceService.find(id).subscribe((taskQuestionInstance) => {
                if (taskQuestionInstance.dueDate) {
                    taskQuestionInstance.dueDate = {
                        year: taskQuestionInstance.dueDate.getFullYear(),
                        month: taskQuestionInstance.dueDate.getMonth() + 1,
                        day: taskQuestionInstance.dueDate.getDate()
                    };
                }
                if (taskQuestionInstance.notifiedDate) {
                    taskQuestionInstance.notifiedDate = {
                        year: taskQuestionInstance.notifiedDate.getFullYear(),
                        month: taskQuestionInstance.notifiedDate.getMonth() + 1,
                        day: taskQuestionInstance.notifiedDate.getDate()
                    };
                }
                if (taskQuestionInstance.completedDate) {
                    taskQuestionInstance.completedDate = {
                        year: taskQuestionInstance.completedDate.getFullYear(),
                        month: taskQuestionInstance.completedDate.getMonth() + 1,
                        day: taskQuestionInstance.completedDate.getDate()
                    };
                }
                this.taskQuestionInstanceModalRef(component, taskQuestionInstance);
            });
        } else {
            return this.taskQuestionInstanceModalRef(component, new TaskQuestionInstance());
        }
    }

    taskQuestionInstanceModalRef(component: Component, taskQuestionInstance: TaskQuestionInstance): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.taskQuestionInstance = taskQuestionInstance;
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
