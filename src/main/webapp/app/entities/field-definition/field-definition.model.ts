import { BaseEntity } from './../../shared';

export class FieldDefinition implements BaseEntity {
    constructor(
        public id?: number,
        public fieldType?: string,
        public key?: string,
        public name?: string,
        public placeHolder?: string,
        public mandatory?: boolean,
        public definitions?: BaseEntity[],
        public definition?: BaseEntity,
    ) {
        this.mandatory = false;
    }
}
