import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    DefinitionConfigService,
    DefinitionConfigPopupService,
    DefinitionConfigComponent,
    DefinitionConfigDetailComponent,
    DefinitionConfigDialogComponent,
    DefinitionConfigPopupComponent,
    DefinitionConfigDeletePopupComponent,
    DefinitionConfigDeleteDialogComponent,
    definitionConfigRoute,
    definitionConfigPopupRoute,
} from './';

const ENTITY_STATES = [
    ...definitionConfigRoute,
    ...definitionConfigPopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DefinitionConfigComponent,
        DefinitionConfigDetailComponent,
        DefinitionConfigDialogComponent,
        DefinitionConfigDeleteDialogComponent,
        DefinitionConfigPopupComponent,
        DefinitionConfigDeletePopupComponent,
    ],
    entryComponents: [
        DefinitionConfigComponent,
        DefinitionConfigDialogComponent,
        DefinitionConfigPopupComponent,
        DefinitionConfigDeleteDialogComponent,
        DefinitionConfigDeletePopupComponent,
    ],
    providers: [
        DefinitionConfigService,
        DefinitionConfigPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerDefinitionConfigModule {}
