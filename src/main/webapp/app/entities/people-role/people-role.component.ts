import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { PeopleRole } from './people-role.model';
import { PeopleRoleService } from './people-role.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-people-role',
    templateUrl: './people-role.component.html'
})
export class PeopleRoleComponent implements OnInit, OnDestroy {
peopleRoles: PeopleRole[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private peopleRoleService: PeopleRoleService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.peopleRoleService.query().subscribe(
            (res: ResponseWrapper) => {
                this.peopleRoles = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPeopleRoles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PeopleRole) {
        return item.id;
    }
    registerChangeInPeopleRoles() {
        this.eventSubscriber = this.eventManager.subscribe('peopleRoleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
