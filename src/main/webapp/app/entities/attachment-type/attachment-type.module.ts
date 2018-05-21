import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    AttachmentTypeService,
    AttachmentTypePopupService,
    AttachmentTypeComponent,
    AttachmentTypeDetailComponent,
    AttachmentTypeDialogComponent,
    AttachmentTypePopupComponent,
    AttachmentTypeDeletePopupComponent,
    AttachmentTypeDeleteDialogComponent,
    attachmentTypeRoute,
    attachmentTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...attachmentTypeRoute,
    ...attachmentTypePopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AttachmentTypeComponent,
        AttachmentTypeDetailComponent,
        AttachmentTypeDialogComponent,
        AttachmentTypeDeleteDialogComponent,
        AttachmentTypePopupComponent,
        AttachmentTypeDeletePopupComponent,
    ],
    entryComponents: [
        AttachmentTypeComponent,
        AttachmentTypeDialogComponent,
        AttachmentTypePopupComponent,
        AttachmentTypeDeleteDialogComponent,
        AttachmentTypeDeletePopupComponent,
    ],
    providers: [
        AttachmentTypeService,
        AttachmentTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerAttachmentTypeModule {}
