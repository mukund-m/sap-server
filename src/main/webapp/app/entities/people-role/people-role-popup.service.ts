import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PeopleRole } from './people-role.model';
import { PeopleRoleService } from './people-role.service';

@Injectable()
export class PeopleRolePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private peopleRoleService: PeopleRoleService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.peopleRoleService.find(id).subscribe((peopleRole) => {
                this.peopleRoleModalRef(component, peopleRole);
            });
        } else {
            return this.peopleRoleModalRef(component, new PeopleRole());
        }
    }

    peopleRoleModalRef(component: Component, peopleRole: PeopleRole): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.peopleRole = peopleRole;
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
