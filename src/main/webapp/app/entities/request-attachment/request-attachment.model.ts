import { BaseEntity } from './../../shared';

export class RequestAttachment implements BaseEntity {
    constructor(
        public id?: number,
        public fileName?: string,
        public uploadedBy?: string,
        public uploadedOn?: any,
        public attachmentType?: string,
        public requestID?: BaseEntity,
    ) {
    }
}
