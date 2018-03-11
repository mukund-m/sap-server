import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FieldOptionDefinitionComponent } from './field-option-definition.component';
import { FieldOptionDefinitionDetailComponent } from './field-option-definition-detail.component';
import { FieldOptionDefinitionPopupComponent } from './field-option-definition-dialog.component';
import { FieldOptionDefinitionDeletePopupComponent } from './field-option-definition-delete-dialog.component';

import { Principal } from '../../shared';

export const fieldOptionDefinitionRoute: Routes = [
    {
        path: 'field-option-definition',
        component: FieldOptionDefinitionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldOptionDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'field-option-definition/:id',
        component: FieldOptionDefinitionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldOptionDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fieldOptionDefinitionPopupRoute: Routes = [
    {
        path: 'field-option-definition-new',
        component: FieldOptionDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldOptionDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'field-option-definition/:id/edit',
        component: FieldOptionDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldOptionDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'field-option-definition/:id/delete',
        component: FieldOptionDefinitionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldOptionDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
