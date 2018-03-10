import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { DefinitionConfig } from './definition-config.model';
import { DefinitionConfigService } from './definition-config.service';

@Component({
    selector: 'jhi-definition-config-detail',
    templateUrl: './definition-config-detail.component.html'
})
export class DefinitionConfigDetailComponent implements OnInit, OnDestroy {

    definitionConfig: DefinitionConfig;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private definitionConfigService: DefinitionConfigService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDefinitionConfigs();
    }

    load(id) {
        this.definitionConfigService.find(id).subscribe((definitionConfig) => {
            this.definitionConfig = definitionConfig;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDefinitionConfigs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'definitionConfigListModification',
            (response) => this.load(this.definitionConfig.id)
        );
    }
}
