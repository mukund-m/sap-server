import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TaskStructureConfigComponent } from './task-structure-config.component';
import { TaskStructureConfigDetailComponent } from './task-structure-config-detail.component';
import { TaskStructureConfigPopupComponent } from './task-structure-config-dialog.component';
import { TaskStructureConfigDeletePopupComponent } from './task-structure-config-delete-dialog.component';

import { Principal } from '../../shared';

export const taskStructureConfigRoute: Routes = [
    {
        path: 'task-structure-config',
        component: TaskStructureConfigComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskStructureConfigs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'task-structure-config/:id',
        component: TaskStructureConfigDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskStructureConfigs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const taskStructureConfigPopupRoute: Routes = [
    {
        path: 'task-structure-config-new',
        component: TaskStructureConfigPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskStructureConfigs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'task-structure-config/:id/edit',
        component: TaskStructureConfigPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskStructureConfigs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'task-structure-config/:id/delete',
        component: TaskStructureConfigDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskStructureConfigs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
