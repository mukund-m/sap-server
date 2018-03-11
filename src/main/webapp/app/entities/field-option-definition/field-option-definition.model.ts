import { BaseEntity } from './../../shared';

export class FieldOptionDefinition implements BaseEntity {
    constructor(
        public id?: number,
        public key?: string,
        public value?: string,
        public fieldDefinition?: BaseEntity,
    ) {
    }
}
