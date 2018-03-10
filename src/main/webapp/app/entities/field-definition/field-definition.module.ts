import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    FieldDefinitionService,
    FieldDefinitionPopupService,
    FieldDefinitionComponent,
    FieldDefinitionDetailComponent,
    FieldDefinitionDialogComponent,
    FieldDefinitionPopupComponent,
    FieldDefinitionDeletePopupComponent,
    FieldDefinitionDeleteDialogComponent,
    fieldDefinitionRoute,
    fieldDefinitionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...fieldDefinitionRoute,
    ...fieldDefinitionPopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FieldDefinitionComponent,
        FieldDefinitionDetailComponent,
        FieldDefinitionDialogComponent,
        FieldDefinitionDeleteDialogComponent,
        FieldDefinitionPopupComponent,
        FieldDefinitionDeletePopupComponent,
    ],
    entryComponents: [
        FieldDefinitionComponent,
        FieldDefinitionDialogComponent,
        FieldDefinitionPopupComponent,
        FieldDefinitionDeleteDialogComponent,
        FieldDefinitionDeletePopupComponent,
    ],
    providers: [
        FieldDefinitionService,
        FieldDefinitionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerFieldDefinitionModule {}
