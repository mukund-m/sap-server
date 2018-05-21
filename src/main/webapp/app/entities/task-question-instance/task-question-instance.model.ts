import { BaseEntity } from './../../shared';

export class TaskQuestionInstance implements BaseEntity {
    constructor(
        public id?: number,
        public sortInParentID?: string,
        public dueDate?: any,
        public notifiedDate?: any,
        public completedDate?: any,
        public status?: string,
        public questionResponse?: string,
        public parentID?: number,
        public definitionID?: number,
        public request?: BaseEntity,
    ) {
    }
}
