import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { PeopleRole } from './people-role.model';
import { PeopleRolePopupService } from './people-role-popup.service';
import { PeopleRoleService } from './people-role.service';

@Component({
    selector: 'jhi-people-role-delete-dialog',
    templateUrl: './people-role-delete-dialog.component.html'
})
export class PeopleRoleDeleteDialogComponent {

    peopleRole: PeopleRole;

    constructor(
        private peopleRoleService: PeopleRoleService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.peopleRoleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'peopleRoleListModification',
                content: 'Deleted an peopleRole'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A People Role is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-people-role-delete-popup',
    template: ''
})
export class PeopleRoleDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private peopleRolePopupService: PeopleRolePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.peopleRolePopupService
                .open(PeopleRoleDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
