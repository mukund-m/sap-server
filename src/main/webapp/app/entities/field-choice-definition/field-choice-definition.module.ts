import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    FieldChoiceDefinitionService,
    FieldChoiceDefinitionPopupService,
    FieldChoiceDefinitionComponent,
    FieldChoiceDefinitionDetailComponent,
    FieldChoiceDefinitionDialogComponent,
    FieldChoiceDefinitionPopupComponent,
    FieldChoiceDefinitionDeletePopupComponent,
    FieldChoiceDefinitionDeleteDialogComponent,
    fieldChoiceDefinitionRoute,
    fieldChoiceDefinitionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...fieldChoiceDefinitionRoute,
    ...fieldChoiceDefinitionPopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FieldChoiceDefinitionComponent,
        FieldChoiceDefinitionDetailComponent,
        FieldChoiceDefinitionDialogComponent,
        FieldChoiceDefinitionDeleteDialogComponent,
        FieldChoiceDefinitionPopupComponent,
        FieldChoiceDefinitionDeletePopupComponent,
    ],
    entryComponents: [
        FieldChoiceDefinitionComponent,
        FieldChoiceDefinitionDialogComponent,
        FieldChoiceDefinitionPopupComponent,
        FieldChoiceDefinitionDeleteDialogComponent,
        FieldChoiceDefinitionDeletePopupComponent,
    ],
    providers: [
        FieldChoiceDefinitionService,
        FieldChoiceDefinitionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerFieldChoiceDefinitionModule {}
