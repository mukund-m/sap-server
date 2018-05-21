import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';

import { PeopleRoleUserMapping } from './people-role-user-mapping.model';
import { PeopleRoleUserMappingService } from './people-role-user-mapping.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-people-role-user-mapping',
    templateUrl: './people-role-user-mapping.component.html'
})
export class PeopleRoleUserMappingComponent implements OnInit, OnDestroy {
peopleRoleUserMappings: PeopleRoleUserMapping[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private peopleRoleUserMappingService: PeopleRoleUserMappingService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.peopleRoleUserMappingService.query().subscribe(
            (res: ResponseWrapper) => {
                this.peopleRoleUserMappings = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPeopleRoleUserMappings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PeopleRoleUserMapping) {
        return item.id;
    }
    registerChangeInPeopleRoleUserMappings() {
        this.eventSubscriber = this.eventManager.subscribe('peopleRoleUserMappingListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
