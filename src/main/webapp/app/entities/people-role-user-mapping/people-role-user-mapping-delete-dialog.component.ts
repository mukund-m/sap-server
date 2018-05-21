import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { PeopleRoleUserMapping } from './people-role-user-mapping.model';
import { PeopleRoleUserMappingPopupService } from './people-role-user-mapping-popup.service';
import { PeopleRoleUserMappingService } from './people-role-user-mapping.service';

@Component({
    selector: 'jhi-people-role-user-mapping-delete-dialog',
    templateUrl: './people-role-user-mapping-delete-dialog.component.html'
})
export class PeopleRoleUserMappingDeleteDialogComponent {

    peopleRoleUserMapping: PeopleRoleUserMapping;

    constructor(
        private peopleRoleUserMappingService: PeopleRoleUserMappingService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.peopleRoleUserMappingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'peopleRoleUserMappingListModification',
                content: 'Deleted an peopleRoleUserMapping'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A People Role User Mapping is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-people-role-user-mapping-delete-popup',
    template: ''
})
export class PeopleRoleUserMappingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private peopleRoleUserMappingPopupService: PeopleRoleUserMappingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.peopleRoleUserMappingPopupService
                .open(PeopleRoleUserMappingDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
