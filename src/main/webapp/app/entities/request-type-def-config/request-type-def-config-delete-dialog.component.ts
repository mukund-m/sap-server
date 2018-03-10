import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { RequestTypeDefConfig } from './request-type-def-config.model';
import { RequestTypeDefConfigPopupService } from './request-type-def-config-popup.service';
import { RequestTypeDefConfigService } from './request-type-def-config.service';

@Component({
    selector: 'jhi-request-type-def-config-delete-dialog',
    templateUrl: './request-type-def-config-delete-dialog.component.html'
})
export class RequestTypeDefConfigDeleteDialogComponent {

    requestTypeDefConfig: RequestTypeDefConfig;

    constructor(
        private requestTypeDefConfigService: RequestTypeDefConfigService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requestTypeDefConfigService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestTypeDefConfigListModification',
                content: 'Deleted an requestTypeDefConfig'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Request Type Def Config is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-request-type-def-config-delete-popup',
    template: ''
})
export class RequestTypeDefConfigDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestTypeDefConfigPopupService: RequestTypeDefConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.requestTypeDefConfigPopupService
                .open(RequestTypeDefConfigDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
