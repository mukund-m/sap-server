import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { ReuestDefinition } from './reuest-definition.model';
import { ReuestDefinitionPopupService } from './reuest-definition-popup.service';
import { ReuestDefinitionService } from './reuest-definition.service';

@Component({
    selector: 'jhi-reuest-definition-delete-dialog',
    templateUrl: './reuest-definition-delete-dialog.component.html'
})
export class ReuestDefinitionDeleteDialogComponent {

    reuestDefinition: ReuestDefinition;

    constructor(
        private reuestDefinitionService: ReuestDefinitionService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reuestDefinitionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reuestDefinitionListModification',
                content: 'Deleted an reuestDefinition'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Reuest Definition is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-reuest-definition-delete-popup',
    template: ''
})
export class ReuestDefinitionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reuestDefinitionPopupService: ReuestDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.reuestDefinitionPopupService
                .open(ReuestDefinitionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
