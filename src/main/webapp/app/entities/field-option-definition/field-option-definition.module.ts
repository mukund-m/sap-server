import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    FieldOptionDefinitionService,
    FieldOptionDefinitionPopupService,
    FieldOptionDefinitionComponent,
    FieldOptionDefinitionDetailComponent,
    FieldOptionDefinitionDialogComponent,
    FieldOptionDefinitionPopupComponent,
    FieldOptionDefinitionDeletePopupComponent,
    FieldOptionDefinitionDeleteDialogComponent,
    fieldOptionDefinitionRoute,
    fieldOptionDefinitionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...fieldOptionDefinitionRoute,
    ...fieldOptionDefinitionPopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FieldOptionDefinitionComponent,
        FieldOptionDefinitionDetailComponent,
        FieldOptionDefinitionDialogComponent,
        FieldOptionDefinitionDeleteDialogComponent,
        FieldOptionDefinitionPopupComponent,
        FieldOptionDefinitionDeletePopupComponent,
    ],
    entryComponents: [
        FieldOptionDefinitionComponent,
        FieldOptionDefinitionDialogComponent,
        FieldOptionDefinitionPopupComponent,
        FieldOptionDefinitionDeleteDialogComponent,
        FieldOptionDefinitionDeletePopupComponent,
    ],
    providers: [
        FieldOptionDefinitionService,
        FieldOptionDefinitionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerFieldOptionDefinitionModule {}
