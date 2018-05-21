import { BaseEntity } from './../../shared';

export class PeopleRole implements BaseEntity {
    constructor(
        public id?: number,
        public roleName?: string,
        public requestTypeDefConfig?: BaseEntity,
    ) {
    }
}
