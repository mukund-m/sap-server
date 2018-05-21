import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PeopleRoleUserMapping } from './people-role-user-mapping.model';
import { PeopleRoleUserMappingService } from './people-role-user-mapping.service';

@Component({
    selector: 'jhi-people-role-user-mapping-detail',
    templateUrl: './people-role-user-mapping-detail.component.html'
})
export class PeopleRoleUserMappingDetailComponent implements OnInit, OnDestroy {

    peopleRoleUserMapping: PeopleRoleUserMapping;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private peopleRoleUserMappingService: PeopleRoleUserMappingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPeopleRoleUserMappings();
    }

    load(id) {
        this.peopleRoleUserMappingService.find(id).subscribe((peopleRoleUserMapping) => {
            this.peopleRoleUserMapping = peopleRoleUserMapping;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPeopleRoleUserMappings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'peopleRoleUserMappingListModification',
            (response) => this.load(this.peopleRoleUserMapping.id)
        );
    }
}
