import { BaseEntity } from './../../shared';

export class Request implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public type?: string,
        public startDate?: any,
        public duration?: string,
        public status?: string,
        public createdOn?: any,
        public modifiedOn?: any,
        public createdBy?: string,
        public modifiedBy?: string,
        public attachments?: BaseEntity[],
        public definitions?: BaseEntity[],
    ) {
    }
}
