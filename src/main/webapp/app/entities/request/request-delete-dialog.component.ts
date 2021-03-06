import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Request } from './request.model';
import { RequestPopupService } from './request-popup.service';
import { RequestService } from './request.service';

@Component({
    selector: 'jhi-request-delete-dialog',
    templateUrl: './request-delete-dialog.component.html'
})
export class RequestDeleteDialogComponent {

    request: Request;

    constructor(
        private requestService: RequestService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requestService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestListModification',
                content: 'Deleted an request'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Request is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-request-delete-popup',
    template: ''
})
export class RequestDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestPopupService: RequestPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.requestPopupService
                .open(RequestDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
