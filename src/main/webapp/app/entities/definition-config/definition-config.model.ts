import { BaseEntity } from './../../shared';

export class DefinitionConfig implements BaseEntity {
    constructor(
        public id?: number,
        public defName?: string,
        public fieldConfigs?: BaseEntity[],
        public reqType?: BaseEntity,
    ) {
    }
}
