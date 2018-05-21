import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FieldChoiceDefinitionComponent } from './field-choice-definition.component';
import { FieldChoiceDefinitionDetailComponent } from './field-choice-definition-detail.component';
import { FieldChoiceDefinitionPopupComponent } from './field-choice-definition-dialog.component';
import { FieldChoiceDefinitionDeletePopupComponent } from './field-choice-definition-delete-dialog.component';

import { Principal } from '../../shared';

export const fieldChoiceDefinitionRoute: Routes = [
    {
        path: 'field-choice-definition',
        component: FieldChoiceDefinitionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldChoiceDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'field-choice-definition/:id',
        component: FieldChoiceDefinitionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldChoiceDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fieldChoiceDefinitionPopupRoute: Routes = [
    {
        path: 'field-choice-definition-new',
        component: FieldChoiceDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldChoiceDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'field-choice-definition/:id/edit',
        component: FieldChoiceDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldChoiceDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'field-choice-definition/:id/delete',
        component: FieldChoiceDefinitionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FieldChoiceDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
