import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AttachmentTypeComponent } from './attachment-type.component';
import { AttachmentTypeDetailComponent } from './attachment-type-detail.component';
import { AttachmentTypePopupComponent } from './attachment-type-dialog.component';
import { AttachmentTypeDeletePopupComponent } from './attachment-type-delete-dialog.component';

import { Principal } from '../../shared';

export const attachmentTypeRoute: Routes = [
    {
        path: 'attachment-type',
        component: AttachmentTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AttachmentTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'attachment-type/:id',
        component: AttachmentTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AttachmentTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attachmentTypePopupRoute: Routes = [
    {
        path: 'attachment-type-new',
        component: AttachmentTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AttachmentTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'attachment-type/:id/edit',
        component: AttachmentTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AttachmentTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'attachment-type/:id/delete',
        component: AttachmentTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AttachmentTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
