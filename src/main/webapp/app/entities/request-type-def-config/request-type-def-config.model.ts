import { BaseEntity } from './../../shared';

export class RequestTypeDefConfig implements BaseEntity {
    constructor(
        public id?: number,
        public requestType?: string,
        public definition?: BaseEntity,
    ) {
    }
}
