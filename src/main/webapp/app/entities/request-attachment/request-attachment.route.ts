import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RequestAttachmentComponent } from './request-attachment.component';
import { RequestAttachmentDetailComponent } from './request-attachment-detail.component';
import { RequestAttachmentPopupComponent } from './request-attachment-dialog.component';
import { RequestAttachmentDeletePopupComponent } from './request-attachment-delete-dialog.component';

import { Principal } from '../../shared';

export const requestAttachmentRoute: Routes = [
    {
        path: 'request-attachment',
        component: RequestAttachmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestAttachments'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'request-attachment/:id',
        component: RequestAttachmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestAttachments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestAttachmentPopupRoute: Routes = [
    {
        path: 'request-attachment-new',
        component: RequestAttachmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-attachment/:id/edit',
        component: RequestAttachmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-attachment/:id/delete',
        component: RequestAttachmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
