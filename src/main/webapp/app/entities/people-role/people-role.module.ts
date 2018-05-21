import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    PeopleRoleService,
    PeopleRolePopupService,
    PeopleRoleComponent,
    PeopleRoleDetailComponent,
    PeopleRoleDialogComponent,
    PeopleRolePopupComponent,
    PeopleRoleDeletePopupComponent,
    PeopleRoleDeleteDialogComponent,
    peopleRoleRoute,
    peopleRolePopupRoute,
} from './';

const ENTITY_STATES = [
    ...peopleRoleRoute,
    ...peopleRolePopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PeopleRoleComponent,
        PeopleRoleDetailComponent,
        PeopleRoleDialogComponent,
        PeopleRoleDeleteDialogComponent,
        PeopleRolePopupComponent,
        PeopleRoleDeletePopupComponent,
    ],
    entryComponents: [
        PeopleRoleComponent,
        PeopleRoleDialogComponent,
        PeopleRolePopupComponent,
        PeopleRoleDeleteDialogComponent,
        PeopleRoleDeletePopupComponent,
    ],
    providers: [
        PeopleRoleService,
        PeopleRolePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerPeopleRoleModule {}
