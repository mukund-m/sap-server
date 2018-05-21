import { BaseEntity } from './../../shared';

export class RefCodeDetails implements BaseEntity {
    constructor(
        public id?: number,
        public category?: string,
        public refCode?: string,
        public refValue?: string,
    ) {
    }
}
