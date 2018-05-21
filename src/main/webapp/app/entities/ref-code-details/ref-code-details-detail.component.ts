import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { RefCodeDetails } from './ref-code-details.model';
import { RefCodeDetailsService } from './ref-code-details.service';

@Component({
    selector: 'jhi-ref-code-details-detail',
    templateUrl: './ref-code-details-detail.component.html'
})
export class RefCodeDetailsDetailComponent implements OnInit, OnDestroy {

    refCodeDetails: RefCodeDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private refCodeDetailsService: RefCodeDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRefCodeDetails();
    }

    load(id) {
        this.refCodeDetailsService.find(id).subscribe((refCodeDetails) => {
            this.refCodeDetails = refCodeDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRefCodeDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'refCodeDetailsListModification',
            (response) => this.load(this.refCodeDetails.id)
        );
    }
}
