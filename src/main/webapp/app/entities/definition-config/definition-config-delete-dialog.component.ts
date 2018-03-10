import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { DefinitionConfig } from './definition-config.model';
import { DefinitionConfigPopupService } from './definition-config-popup.service';
import { DefinitionConfigService } from './definition-config.service';

@Component({
    selector: 'jhi-definition-config-delete-dialog',
    templateUrl: './definition-config-delete-dialog.component.html'
})
export class DefinitionConfigDeleteDialogComponent {

    definitionConfig: DefinitionConfig;

    constructor(
        private definitionConfigService: DefinitionConfigService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.definitionConfigService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'definitionConfigListModification',
                content: 'Deleted an definitionConfig'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Definition Config is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-definition-config-delete-popup',
    template: ''
})
export class DefinitionConfigDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private definitionConfigPopupService: DefinitionConfigPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.definitionConfigPopupService
                .open(DefinitionConfigDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
