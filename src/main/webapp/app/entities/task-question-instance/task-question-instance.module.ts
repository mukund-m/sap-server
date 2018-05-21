import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    TaskQuestionInstanceService,
    TaskQuestionInstancePopupService,
    TaskQuestionInstanceComponent,
    TaskQuestionInstanceDetailComponent,
    TaskQuestionInstanceDialogComponent,
    TaskQuestionInstancePopupComponent,
    TaskQuestionInstanceDeletePopupComponent,
    TaskQuestionInstanceDeleteDialogComponent,
    taskQuestionInstanceRoute,
    taskQuestionInstancePopupRoute,
} from './';

const ENTITY_STATES = [
    ...taskQuestionInstanceRoute,
    ...taskQuestionInstancePopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TaskQuestionInstanceComponent,
        TaskQuestionInstanceDetailComponent,
        TaskQuestionInstanceDialogComponent,
        TaskQuestionInstanceDeleteDialogComponent,
        TaskQuestionInstancePopupComponent,
        TaskQuestionInstanceDeletePopupComponent,
    ],
    entryComponents: [
        TaskQuestionInstanceComponent,
        TaskQuestionInstanceDialogComponent,
        TaskQuestionInstancePopupComponent,
        TaskQuestionInstanceDeleteDialogComponent,
        TaskQuestionInstanceDeletePopupComponent,
    ],
    providers: [
        TaskQuestionInstanceService,
        TaskQuestionInstancePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerTaskQuestionInstanceModule {}
