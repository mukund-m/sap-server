import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    TaskStructureConfigService,
    TaskStructureConfigPopupService,
    TaskStructureConfigComponent,
    TaskStructureConfigDetailComponent,
    TaskStructureConfigDialogComponent,
    TaskStructureConfigPopupComponent,
    TaskStructureConfigDeletePopupComponent,
    TaskStructureConfigDeleteDialogComponent,
    taskStructureConfigRoute,
    taskStructureConfigPopupRoute,
} from './';

const ENTITY_STATES = [
    ...taskStructureConfigRoute,
    ...taskStructureConfigPopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TaskStructureConfigComponent,
        TaskStructureConfigDetailComponent,
        TaskStructureConfigDialogComponent,
        TaskStructureConfigDeleteDialogComponent,
        TaskStructureConfigPopupComponent,
        TaskStructureConfigDeletePopupComponent,
    ],
    entryComponents: [
        TaskStructureConfigComponent,
        TaskStructureConfigDialogComponent,
        TaskStructureConfigPopupComponent,
        TaskStructureConfigDeleteDialogComponent,
        TaskStructureConfigDeletePopupComponent,
    ],
    providers: [
        TaskStructureConfigService,
        TaskStructureConfigPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerTaskStructureConfigModule {}
