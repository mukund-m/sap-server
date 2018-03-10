import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ReuestDefinition } from './reuest-definition.model';
import { ReuestDefinitionService } from './reuest-definition.service';

@Component({
    selector: 'jhi-reuest-definition-detail',
    templateUrl: './reuest-definition-detail.component.html'
})
export class ReuestDefinitionDetailComponent implements OnInit, OnDestroy {

    reuestDefinition: ReuestDefinition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private reuestDefinitionService: ReuestDefinitionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReuestDefinitions();
    }

    load(id) {
        this.reuestDefinitionService.find(id).subscribe((reuestDefinition) => {
            this.reuestDefinition = reuestDefinition;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReuestDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reuestDefinitionListModification',
            (response) => this.load(this.reuestDefinition.id)
        );
    }
}
