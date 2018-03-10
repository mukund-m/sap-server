import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    RequestAttachmentService,
    RequestAttachmentPopupService,
    RequestAttachmentComponent,
    RequestAttachmentDetailComponent,
    RequestAttachmentDialogComponent,
    RequestAttachmentPopupComponent,
    RequestAttachmentDeletePopupComponent,
    RequestAttachmentDeleteDialogComponent,
    requestAttachmentRoute,
    requestAttachmentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...requestAttachmentRoute,
    ...requestAttachmentPopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RequestAttachmentComponent,
        RequestAttachmentDetailComponent,
        RequestAttachmentDialogComponent,
        RequestAttachmentDeleteDialogComponent,
        RequestAttachmentPopupComponent,
        RequestAttachmentDeletePopupComponent,
    ],
    entryComponents: [
        RequestAttachmentComponent,
        RequestAttachmentDialogComponent,
        RequestAttachmentPopupComponent,
        RequestAttachmentDeleteDialogComponent,
        RequestAttachmentDeletePopupComponent,
    ],
    providers: [
        RequestAttachmentService,
        RequestAttachmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerRequestAttachmentModule {}
