import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReuestDefinitionComponent } from './reuest-definition.component';
import { ReuestDefinitionDetailComponent } from './reuest-definition-detail.component';
import { ReuestDefinitionPopupComponent } from './reuest-definition-dialog.component';
import { ReuestDefinitionDeletePopupComponent } from './reuest-definition-delete-dialog.component';

import { Principal } from '../../shared';

export const reuestDefinitionRoute: Routes = [
    {
        path: 'reuest-definition',
        component: ReuestDefinitionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReuestDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reuest-definition/:id',
        component: ReuestDefinitionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReuestDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reuestDefinitionPopupRoute: Routes = [
    {
        path: 'reuest-definition-new',
        component: ReuestDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReuestDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reuest-definition/:id/edit',
        component: ReuestDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReuestDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reuest-definition/:id/delete',
        component: ReuestDefinitionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReuestDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
