import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TaskQuestionInstanceComponent } from './task-question-instance.component';
import { TaskQuestionInstanceDetailComponent } from './task-question-instance-detail.component';
import { TaskQuestionInstancePopupComponent } from './task-question-instance-dialog.component';
import { TaskQuestionInstanceDeletePopupComponent } from './task-question-instance-delete-dialog.component';

import { Principal } from '../../shared';

export const taskQuestionInstanceRoute: Routes = [
    {
        path: 'task-question-instance',
        component: TaskQuestionInstanceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskQuestionInstances'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'task-question-instance/:id',
        component: TaskQuestionInstanceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskQuestionInstances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const taskQuestionInstancePopupRoute: Routes = [
    {
        path: 'task-question-instance-new',
        component: TaskQuestionInstancePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskQuestionInstances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'task-question-instance/:id/edit',
        component: TaskQuestionInstancePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskQuestionInstances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'task-question-instance/:id/delete',
        component: TaskQuestionInstanceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TaskQuestionInstances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
