import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefCodeDetailsComponent } from './ref-code-details.component';
import { RefCodeDetailsDetailComponent } from './ref-code-details-detail.component';
import { RefCodeDetailsPopupComponent } from './ref-code-details-dialog.component';
import { RefCodeDetailsDeletePopupComponent } from './ref-code-details-delete-dialog.component';

import { Principal } from '../../shared';

export const refCodeDetailsRoute: Routes = [
    {
        path: 'ref-code-details',
        component: RefCodeDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RefCodeDetails'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-code-details/:id',
        component: RefCodeDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RefCodeDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refCodeDetailsPopupRoute: Routes = [
    {
        path: 'ref-code-details-new',
        component: RefCodeDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RefCodeDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-code-details/:id/edit',
        component: RefCodeDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RefCodeDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-code-details/:id/delete',
        component: RefCodeDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RefCodeDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
