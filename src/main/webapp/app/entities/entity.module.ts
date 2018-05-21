import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ChangeAppServerRequestModule } from './request/request.module';
import { ChangeAppServerRequestAttachmentModule } from './request-attachment/request-attachment.module';
import { ChangeAppServerDefinitionConfigModule } from './definition-config/definition-config.module';
import { ChangeAppServerRequestTypeDefConfigModule } from './request-type-def-config/request-type-def-config.module';
import { ChangeAppServerFieldDefinitionModule } from './field-definition/field-definition.module';
import { ChangeAppServerReuestDefinitionModule } from './reuest-definition/reuest-definition.module';
import { ChangeAppServerFieldOptionDefinitionModule } from './field-option-definition/field-option-definition.module';
import { ChangeAppServerFieldChoiceDefinitionModule } from './field-choice-definition/field-choice-definition.module';
import { ChangeAppServerRefCodeDetailsModule } from './ref-code-details/ref-code-details.module';
import { ChangeAppServerAttachmentTypeModule } from './attachment-type/attachment-type.module';
import { ChangeAppServerTaskStructureConfigModule } from './task-structure-config/task-structure-config.module';
import { ChangeAppServerPeopleRoleModule } from './people-role/people-role.module';
import { ChangeAppServerPeopleRoleUserMappingModule } from './people-role-user-mapping/people-role-user-mapping.module';
import { ChangeAppServerTaskQuestionInstanceModule } from './task-question-instance/task-question-instance.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ChangeAppServerRequestModule,
        ChangeAppServerRequestAttachmentModule,
        ChangeAppServerDefinitionConfigModule,
        ChangeAppServerRequestTypeDefConfigModule,
        ChangeAppServerFieldDefinitionModule,
        ChangeAppServerReuestDefinitionModule,
        ChangeAppServerFieldOptionDefinitionModule,
        ChangeAppServerFieldChoiceDefinitionModule,
        ChangeAppServerRefCodeDetailsModule,
        ChangeAppServerAttachmentTypeModule,
        ChangeAppServerTaskStructureConfigModule,
        ChangeAppServerPeopleRoleModule,
        ChangeAppServerPeopleRoleUserMappingModule,
        ChangeAppServerTaskQuestionInstanceModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChangeAppServerEntityModule {}
