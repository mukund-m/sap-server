import { BaseEntity } from './../../shared';

export class FieldChoiceDefinition implements BaseEntity {
    constructor(
        public id?: number,
        public key?: string,
        public value?: string,
        public fieldDefinition?: BaseEntity,
    ) {
    }
}
