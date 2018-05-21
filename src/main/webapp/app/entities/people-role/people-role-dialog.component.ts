import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PeopleRole } from './people-role.model';
import { PeopleRolePopupService } from './people-role-popup.service';
import { PeopleRoleService } from './people-role.service';
import { RequestTypeDefConfig, RequestTypeDefConfigService } from '../request-type-def-config';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-people-role-dialog',
    templateUrl: './people-role-dialog.component.html'
})
export class PeopleRoleDialogComponent implements OnInit {

    peopleRole: PeopleRole;
    authorities: any[];
    isSaving: boolean;

    requesttypedefconfigs: RequestTypeDefConfig[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private peopleRoleService: PeopleRoleService,
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
        if (this.peopleRole.id !== undefined) {
            this.subscribeToSaveResponse(
                this.peopleRoleService.update(this.peopleRole), false);
        } else {
            this.subscribeToSaveResponse(
                this.peopleRoleService.create(this.peopleRole), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PeopleRole>, isCreated: boolean) {
        result.subscribe((res: PeopleRole) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PeopleRole, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new People Role is created with identifier ${result.id}`
            : `A People Role is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'peopleRoleListModification', content: 'OK'});
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
    selector: 'jhi-people-role-popup',
    template: ''
})
export class PeopleRolePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private peopleRolePopupService: PeopleRolePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.peopleRolePopupService
                    .open(PeopleRoleDialogComponent, params['id']);
            } else {
                this.modalRef = this.peopleRolePopupService
                    .open(PeopleRoleDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
