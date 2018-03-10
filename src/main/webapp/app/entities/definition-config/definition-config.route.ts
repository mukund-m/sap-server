import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DefinitionConfigComponent } from './definition-config.component';
import { DefinitionConfigDetailComponent } from './definition-config-detail.component';
import { DefinitionConfigPopupComponent } from './definition-config-dialog.component';
import { DefinitionConfigDeletePopupComponent } from './definition-config-delete-dialog.component';

import { Principal } from '../../shared';

export const definitionConfigRoute: Routes = [
    {
        path: 'definition-config',
        component: DefinitionConfigComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefinitionConfigs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'definition-config/:id',
        component: DefinitionConfigDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefinitionConfigs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const definitionConfigPopupRoute: Routes = [
    {
        path: 'definition-config-new',
        component: DefinitionConfigPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefinitionConfigs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'definition-config/:id/edit',
        component: DefinitionConfigPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefinitionConfigs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'definition-config/:id/delete',
        component: DefinitionConfigDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DefinitionConfigs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
