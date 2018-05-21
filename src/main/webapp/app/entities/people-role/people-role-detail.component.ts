import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PeopleRole } from './people-role.model';
import { PeopleRoleService } from './people-role.service';

@Component({
    selector: 'jhi-people-role-detail',
    templateUrl: './people-role-detail.component.html'
})
export class PeopleRoleDetailComponent implements OnInit, OnDestroy {

    peopleRole: PeopleRole;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private peopleRoleService: PeopleRoleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPeopleRoles();
    }

    load(id) {
        this.peopleRoleService.find(id).subscribe((peopleRole) => {
            this.peopleRole = peopleRole;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPeopleRoles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'peopleRoleListModification',
            (response) => this.load(this.peopleRole.id)
        );
    }
}
