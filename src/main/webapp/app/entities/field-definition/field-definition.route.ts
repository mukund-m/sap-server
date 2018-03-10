import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FieldDefinitionComponent } from './field-definition.component';
import { FieldDefinitionDetailComponent } from './field-definition-detail.component';
import { FieldDefinitionPopupComponent } from './field-definition-dialog.component';
import { FieldDefinitionDeletePopupComponent } from './field-definition-delete-dialog.component';

import { Principal } from '../../shared';

export const fieldDefinitionRoute: Routes = [
    {
        path: 'field-definition',
        component: FieldDefinitionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'field-definition/:id',
        component: FieldDefinitionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fieldDefinitionPopupRoute: Routes = [
    {
        path: 'field-definition-new',
        component: FieldDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'field-definition/:id/edit',
        component: FieldDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'field-definition/:id/delete',
        component: FieldDefinitionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
