import { BaseEntity } from './../../shared';

export class AttachmentType implements BaseEntity {
    constructor(
        public id?: number,
        public typeName?: string,
        public description?: string,
        public requestTypeDefConfig?: BaseEntity,
    ) {
    }
}
