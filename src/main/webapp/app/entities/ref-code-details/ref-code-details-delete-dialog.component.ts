import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { RefCodeDetails } from './ref-code-details.model';
import { RefCodeDetailsPopupService } from './ref-code-details-popup.service';
import { RefCodeDetailsService } from './ref-code-details.service';

@Component({
    selector: 'jhi-ref-code-details-delete-dialog',
    templateUrl: './ref-code-details-delete-dialog.component.html'
})
export class RefCodeDetailsDeleteDialogComponent {

    refCodeDetails: RefCodeDetails;

    constructor(
        private refCodeDetailsService: RefCodeDetailsService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.refCodeDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'refCodeDetailsListModification',
                content: 'Deleted an refCodeDetails'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Ref Code Details is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-ref-code-details-delete-popup',
    template: ''
})
export class RefCodeDetailsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refCodeDetailsPopupService: RefCodeDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.refCodeDetailsPopupService
                .open(RefCodeDetailsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
