import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    RequestTypeDefConfigService,
    RequestTypeDefConfigPopupService,
    RequestTypeDefConfigComponent,
    RequestTypeDefConfigDetailComponent,
    RequestTypeDefConfigDialogComponent,
    RequestTypeDefConfigPopupComponent,
    RequestTypeDefConfigDeletePopupComponent,
    RequestTypeDefConfigDeleteDialogComponent,
    requestTypeDefConfigRoute,
    requestTypeDefConfigPopupRoute,
} from './';

const ENTITY_STATES = [
    ...requestTypeDefConfigRoute,
    ...requestTypeDefConfigPopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RequestTypeDefConfigComponent,
        RequestTypeDefConfigDetailComponent,
        RequestTypeDefConfigDialogComponent,
        RequestTypeDefConfigDeleteDialogComponent,
        RequestTypeDefConfigPopupComponent,
        RequestTypeDefConfigDeletePopupComponent,
    ],
    entryComponents: [
        RequestTypeDefConfigComponent,
        RequestTypeDefConfigDialogComponent,
        RequestTypeDefConfigPopupComponent,
        RequestTypeDefConfigDeleteDialogComponent,
        RequestTypeDefConfigDeletePopupComponent,
    ],
    providers: [
        RequestTypeDefConfigService,
        RequestTypeDefConfigPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerRequestTypeDefConfigModule {}
