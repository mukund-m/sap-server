import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RequestTypeDefConfigComponent } from './request-type-def-config.component';
import { RequestTypeDefConfigDetailComponent } from './request-type-def-config-detail.component';
import { RequestTypeDefConfigPopupComponent } from './request-type-def-config-dialog.component';
import { RequestTypeDefConfigDeletePopupComponent } from './request-type-def-config-delete-dialog.component';

import { Principal } from '../../shared';

export const requestTypeDefConfigRoute: Routes = [
    {
        path: 'request-type-def-config',
        component: RequestTypeDefConfigComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestTypeDefConfigs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'request-type-def-config/:id',
        component: RequestTypeDefConfigDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestTypeDefConfigs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestTypeDefConfigPopupRoute: Routes = [
    {
        path: 'request-type-def-config-new',
        component: RequestTypeDefConfigPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestTypeDefConfigs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-type-def-config/:id/edit',
        component: RequestTypeDefConfigPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestTypeDefConfigs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-type-def-config/:id/delete',
        component: RequestTypeDefConfigDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestTypeDefConfigs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
