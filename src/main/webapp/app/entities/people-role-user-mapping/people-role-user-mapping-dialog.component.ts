import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PeopleRoleUserMapping } from './people-role-user-mapping.model';
import { PeopleRoleUserMappingPopupService } from './people-role-user-mapping-popup.service';
import { PeopleRoleUserMappingService } from './people-role-user-mapping.service';
import { RequestTypeDefConfig, RequestTypeDefConfigService } from '../request-type-def-config';
import { PeopleRole, PeopleRoleService } from '../people-role';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-people-role-user-mapping-dialog',
    templateUrl: './people-role-user-mapping-dialog.component.html'
})
export class PeopleRoleUserMappingDialogComponent implements OnInit {

    peopleRoleUserMapping: PeopleRoleUserMapping;
    authorities: any[];
    isSaving: boolean;

    requesttypedefconfigs: RequestTypeDefConfig[];

    peopleroles: PeopleRole[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private peopleRoleUserMappingService: PeopleRoleUserMappingService,
        private requestTypeDefConfigService: RequestTypeDefConfigService,
        private peopleRoleService: PeopleRoleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.requestTypeDefConfigService.query()
            .subscribe((res: ResponseWrapper) => { this.requesttypedefconfigs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.peopleRoleService.query()
            .subscribe((res: ResponseWrapper) => { this.peopleroles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.peopleRoleUserMapping.id !== undefined) {
            this.subscribeToSaveResponse(
                this.peopleRoleUserMappingService.update(this.peopleRoleUserMapping), false);
        } else {
            this.subscribeToSaveResponse(
                this.peopleRoleUserMappingService.create(this.peopleRoleUserMapping), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PeopleRoleUserMapping>, isCreated: boolean) {
        result.subscribe((res: PeopleRoleUserMapping) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PeopleRoleUserMapping, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new People Role User Mapping is created with identifier ${result.id}`
            : `A People Role User Mapping is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'peopleRoleUserMappingListModification', content: 'OK'});
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

    trackPeopleRoleById(index: number, item: PeopleRole) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-people-role-user-mapping-popup',
    template: ''
})
export class PeopleRoleUserMappingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private peopleRoleUserMappingPopupService: PeopleRoleUserMappingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.peopleRoleUserMappingPopupService
                    .open(PeopleRoleUserMappingDialogComponent, params['id']);
            } else {
                this.modalRef = this.peopleRoleUserMappingPopupService
                    .open(PeopleRoleUserMappingDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
