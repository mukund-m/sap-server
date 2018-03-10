import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { FieldDefinition } from './field-definition.model';
import { FieldDefinitionService } from './field-definition.service';

@Component({
    selector: 'jhi-field-definition-detail',
    templateUrl: './field-definition-detail.component.html'
})
export class FieldDefinitionDetailComponent implements OnInit, OnDestroy {

    fieldDefinition: FieldDefinition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fieldDefinitionService: FieldDefinitionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFieldDefinitions();
    }

    load(id) {
        this.fieldDefinitionService.find(id).subscribe((fieldDefinition) => {
            this.fieldDefinition = fieldDefinition;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFieldDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fieldDefinitionListModification',
            (response) => this.load(this.fieldDefinition.id)
        );
    }
}
