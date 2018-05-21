import { BaseEntity } from './../../shared';

export class TaskStructureConfig implements BaseEntity {
    constructor(
        public id?: number,
        public parentID?: number,
        public sortInParentID?: string,
        public name?: string,
        public type?: string,
        public questionType?: string,
        public questionAnswerListID?: number,
        public peopleRole?: number,
        public order?: number,
        public instruction?: string,
        public requestTypeDefConfig?: BaseEntity,
    ) {
    }
}
