import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefCodeDetails } from './ref-code-details.model';
import { RefCodeDetailsPopupService } from './ref-code-details-popup.service';
import { RefCodeDetailsService } from './ref-code-details.service';

@Component({
    selector: 'jhi-ref-code-details-dialog',
    templateUrl: './ref-code-details-dialog.component.html'
})
export class RefCodeDetailsDialogComponent implements OnInit {

    refCodeDetails: RefCodeDetails;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refCodeDetailsService: RefCodeDetailsService,
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
        if (this.refCodeDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refCodeDetailsService.update(this.refCodeDetails), false);
        } else {
            this.subscribeToSaveResponse(
                this.refCodeDetailsService.create(this.refCodeDetails), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<RefCodeDetails>, isCreated: boolean) {
        result.subscribe((res: RefCodeDetails) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefCodeDetails, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Ref Code Details is created with identifier ${result.id}`
            : `A Ref Code Details is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'refCodeDetailsListModification', content: 'OK'});
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
    selector: 'jhi-ref-code-details-popup',
    template: ''
})
export class RefCodeDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refCodeDetailsPopupService: RefCodeDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.refCodeDetailsPopupService
                    .open(RefCodeDetailsDialogComponent, params['id']);
            } else {
                this.modalRef = this.refCodeDetailsPopupService
                    .open(RefCodeDetailsDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
