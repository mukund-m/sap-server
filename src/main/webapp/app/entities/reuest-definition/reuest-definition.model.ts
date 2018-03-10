import { BaseEntity } from './../../shared';

export class ReuestDefinition implements BaseEntity {
    constructor(
        public id?: number,
        public value?: string,
        public request?: BaseEntity,
        public fieldDefinition?: BaseEntity,
    ) {
    }
}
