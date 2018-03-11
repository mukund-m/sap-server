import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { FieldOptionDefinition } from './field-option-definition.model';
import { FieldOptionDefinitionService } from './field-option-definition.service';

@Component({
    selector: 'jhi-field-option-definition-detail',
    templateUrl: './field-option-definition-detail.component.html'
})
export class FieldOptionDefinitionDetailComponent implements OnInit, OnDestroy {

    fieldOptionDefinition: FieldOptionDefinition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fieldOptionDefinitionService: FieldOptionDefinitionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFieldOptionDefinitions();
    }

    load(id) {
        this.fieldOptionDefinitionService.find(id).subscribe((fieldOptionDefinition) => {
            this.fieldOptionDefinition = fieldOptionDefinition;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFieldOptionDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fieldOptionDefinitionListModification',
            (response) => this.load(this.fieldOptionDefinition.id)
        );
    }
}
