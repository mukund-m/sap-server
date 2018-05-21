import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { FieldChoiceDefinition } from './field-choice-definition.model';
import { FieldChoiceDefinitionService } from './field-choice-definition.service';

@Component({
    selector: 'jhi-field-choice-definition-detail',
    templateUrl: './field-choice-definition-detail.component.html'
})
export class FieldChoiceDefinitionDetailComponent implements OnInit, OnDestroy {

    fieldChoiceDefinition: FieldChoiceDefinition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fieldChoiceDefinitionService: FieldChoiceDefinitionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFieldChoiceDefinitions();
    }

    load(id) {
        this.fieldChoiceDefinitionService.find(id).subscribe((fieldChoiceDefinition) => {
            this.fieldChoiceDefinition = fieldChoiceDefinition;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFieldChoiceDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fieldChoiceDefinitionListModification',
            (response) => this.load(this.fieldChoiceDefinition.id)
        );
    }
}
