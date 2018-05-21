import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PeopleRoleComponent } from './people-role.component';
import { PeopleRoleDetailComponent } from './people-role-detail.component';
import { PeopleRolePopupComponent } from './people-role-dialog.component';
import { PeopleRoleDeletePopupComponent } from './people-role-delete-dialog.component';

import { Principal } from '../../shared';

export const peopleRoleRoute: Routes = [
    {
        path: 'people-role',
        component: PeopleRoleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeopleRoles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'people-role/:id',
        component: PeopleRoleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeopleRoles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const peopleRolePopupRoute: Routes = [
    {
        path: 'people-role-new',
        component: PeopleRolePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeopleRoles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'people-role/:id/edit',
        component: PeopleRolePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeopleRoles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'people-role/:id/delete',
        component: PeopleRoleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeopleRoles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
