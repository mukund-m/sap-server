import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RequestTypeDefConfig } from './request-type-def-config.model';
import { RequestTypeDefConfigService } from './request-type-def-config.service';

@Component({
    selector: 'jhi-request-type-def-config-detail',
    templateUrl: './request-type-def-config-detail.component.html'
})
export class RequestTypeDefConfigDetailComponent implements OnInit, OnDestroy {

    requestTypeDefConfig: RequestTypeDefConfig;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private requestTypeDefConfigService: RequestTypeDefConfigService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRequestTypeDefConfigs();
    }

    load(id) {
        this.requestTypeDefConfigService.find(id).subscribe((requestTypeDefConfig) => {
            this.requestTypeDefConfig = requestTypeDefConfig;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRequestTypeDefConfigs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'requestTypeDefConfigListModification',
            (response) => this.load(this.requestTypeDefConfig.id)
        );
    }
}
