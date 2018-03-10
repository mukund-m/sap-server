import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Request } from './request.model';
import { RequestPopupService } from './request-popup.service';
import { RequestService } from './request.service';

@Component({
    selector: 'jhi-request-dialog',
    templateUrl: './request-dialog.component.html'
})
export class RequestDialogComponent implements OnInit {

    request: Request;
    authorities: any[];
    isSaving: boolean;
    startDateDp: any;
    createdOnDp: any;
    modifiedOnDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private requestService: RequestService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.request.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestService.update(this.request), false);
        } else {
            this.subscribeToSaveResponse(
                this.requestService.create(this.request), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Request>, isCreated: boolean) {
        result.subscribe((res: Request) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Request, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Request is created with identifier ${result.id}`
            : `A Request is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'requestListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-request-popup',
    template: ''
})
export class RequestPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestPopupService: RequestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.requestPopupService
                    .open(RequestDialogComponent, params['id']);
            } else {
                this.modalRef = this.requestPopupService
                    .open(RequestDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
