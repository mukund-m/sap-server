import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChangeAppServerSharedModule } from '../../shared';
import {
    PeopleRoleUserMappingService,
    PeopleRoleUserMappingPopupService,
    PeopleRoleUserMappingComponent,
    PeopleRoleUserMappingDetailComponent,
    PeopleRoleUserMappingDialogComponent,
    PeopleRoleUserMappingPopupComponent,
    PeopleRoleUserMappingDeletePopupComponent,
    PeopleRoleUserMappingDeleteDialogComponent,
    peopleRoleUserMappingRoute,
    peopleRoleUserMappingPopupRoute,
} from './';

const ENTITY_STATES = [
    ...peopleRoleUserMappingRoute,
    ...peopleRoleUserMappingPopupRoute,
];

@NgModule({
    imports: [
        ChangeAppServerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PeopleRoleUserMappingComponent,
        PeopleRoleUserMappingDetailComponent,
        PeopleRoleUserMappingDialogComponent,
        PeopleRoleUserMappingDeleteDialogComponent,
        PeopleRoleUserMappingPopupComponent,
        PeopleRoleUserMappingDeletePopupComponent,
    ],
    entryComponents: [
        PeopleRoleUserMappingComponent,
        PeopleRoleUserMappingDialogComponent,
        PeopleRoleUserMappingPopupComponent,
        PeopleRoleUserMappingDeleteDialogComponent,
        PeopleRoleUserMappingDeletePopupComponent,
    ],
    providers: [
        PeopleRoleUserMappingService,
        PeopleRoleUserMappingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerPeopleRoleUserMappingModule {}
