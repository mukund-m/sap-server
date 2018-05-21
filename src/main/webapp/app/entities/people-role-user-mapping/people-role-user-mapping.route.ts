import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PeopleRoleUserMappingComponent } from './people-role-user-mapping.component';
import { PeopleRoleUserMappingDetailComponent } from './people-role-user-mapping-detail.component';
import { PeopleRoleUserMappingPopupComponent } from './people-role-user-mapping-dialog.component';
import { PeopleRoleUserMappingDeletePopupComponent } from './people-role-user-mapping-delete-dialog.component';

import { Principal } from '../../shared';

export const peopleRoleUserMappingRoute: Routes = [
    {
        path: 'people-role-user-mapping',
        component: PeopleRoleUserMappingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeopleRoleUserMappings'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'people-role-user-mapping/:id',
        component: PeopleRoleUserMappingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeopleRoleUserMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const peopleRoleUserMappingPopupRoute: Routes = [
    {
        path: 'people-role-user-mapping-new',
        component: PeopleRoleUserMappingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeopleRoleUserMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'people-role-user-mapping/:id/edit',
        component: PeopleRoleUserMappingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeopleRoleUserMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'people-role-user-mapping/:id/delete',
        component: PeopleRoleUserMappingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeopleRoleUserMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
