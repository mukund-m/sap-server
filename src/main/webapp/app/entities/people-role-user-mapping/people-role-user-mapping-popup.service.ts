import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PeopleRoleUserMapping } from './people-role-user-mapping.model';
import { PeopleRoleUserMappingService } from './people-role-user-mapping.service';

@Injectable()
export class PeopleRoleUserMappingPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private peopleRoleUserMappingService: PeopleRoleUserMappingService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.peopleRoleUserMappingService.find(id).subscribe((peopleRoleUserMapping) => {
                this.peopleRoleUserMappingModalRef(component, peopleRoleUserMapping);
            });
        } else {
            return this.peopleRoleUserMappingModalRef(component, new PeopleRoleUserMapping());
        }
    }

    peopleRoleUserMappingModalRef(component: Component, peopleRoleUserMapping: PeopleRoleUserMapping): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.peopleRoleUserMapping = peopleRoleUserMapping;
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
