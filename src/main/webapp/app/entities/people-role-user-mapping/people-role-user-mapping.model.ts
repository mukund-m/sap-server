import { BaseEntity } from './../../shared';

export class PeopleRoleUserMapping implements BaseEntity {
    constructor(
        public id?: number,
        public userID?: string,
        public requestTypeDefConfig?: BaseEntity,
        public peopleRole?: BaseEntity,
    ) {
    }
}
