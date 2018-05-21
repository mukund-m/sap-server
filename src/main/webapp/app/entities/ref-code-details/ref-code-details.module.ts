import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    RefCodeDetailsService,
    RefCodeDetailsPopupService,
    RefCodeDetailsComponent,
    RefCodeDetailsDetailComponent,
    RefCodeDetailsDialogComponent,
    RefCodeDetailsPopupComponent,
    RefCodeDetailsDeletePopupComponent,
    RefCodeDetailsDeleteDialogComponent,
    refCodeDetailsRoute,
    refCodeDetailsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...refCodeDetailsRoute,
    ...refCodeDetailsPopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefCodeDetailsComponent,
        RefCodeDetailsDetailComponent,
        RefCodeDetailsDialogComponent,
        RefCodeDetailsDeleteDialogComponent,
        RefCodeDetailsPopupComponent,
        RefCodeDetailsDeletePopupComponent,
    ],
    entryComponents: [
        RefCodeDetailsComponent,
        RefCodeDetailsDialogComponent,
        RefCodeDetailsPopupComponent,
        RefCodeDetailsDeleteDialogComponent,
        RefCodeDetailsDeletePopupComponent,
    ],
    providers: [
        RefCodeDetailsService,
        RefCodeDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerRefCodeDetailsModule {}
