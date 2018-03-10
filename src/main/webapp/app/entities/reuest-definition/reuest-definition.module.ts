import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    ReuestDefinitionService,
    ReuestDefinitionPopupService,
    ReuestDefinitionComponent,
    ReuestDefinitionDetailComponent,
    ReuestDefinitionDialogComponent,
    ReuestDefinitionPopupComponent,
    ReuestDefinitionDeletePopupComponent,
    ReuestDefinitionDeleteDialogComponent,
    reuestDefinitionRoute,
    reuestDefinitionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...reuestDefinitionRoute,
    ...reuestDefinitionPopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReuestDefinitionComponent,
        ReuestDefinitionDetailComponent,
        ReuestDefinitionDialogComponent,
        ReuestDefinitionDeleteDialogComponent,
        ReuestDefinitionPopupComponent,
        ReuestDefinitionDeletePopupComponent,
    ],
    entryComponents: [
        ReuestDefinitionComponent,
        ReuestDefinitionDialogComponent,
        ReuestDefinitionPopupComponent,
        ReuestDefinitionDeleteDialogComponent,
        ReuestDefinitionDeletePopupComponent,
    ],
    providers: [
        ReuestDefinitionService,
        ReuestDefinitionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerReuestDefinitionModule {}
