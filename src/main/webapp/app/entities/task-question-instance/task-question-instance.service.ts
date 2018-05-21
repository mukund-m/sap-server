import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { TaskQuestionInstance } from './task-question-instance.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TaskQuestionInstanceService {

    private resourceUrl = 'api/task-question-instances';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(taskQuestionInstance: TaskQuestionInstance): Observable<TaskQuestionInstance> {
        const copy = this.convert(taskQuestionInstance);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(taskQuestionInstance: TaskQuestionInstance): Observable<TaskQuestionInstance> {
        const copy = this.convert(taskQuestionInstance);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<TaskQuestionInstance> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dueDate = this.dateUtils
            .convertLocalDateFromServer(entity.dueDate);
        entity.notifiedDate = this.dateUtils
            .convertLocalDateFromServer(entity.notifiedDate);
        entity.completedDate = this.dateUtils
            .convertLocalDateFromServer(entity.completedDate);
    }

    private convert(taskQuestionInstance: TaskQuestionInstance): TaskQuestionInstance {
        const copy: TaskQuestionInstance = Object.assign({}, taskQuestionInstance);
        copy.dueDate = this.dateUtils
            .convertLocalDateToServer(taskQuestionInstance.dueDate);
        copy.notifiedDate = this.dateUtils
            .convertLocalDateToServer(taskQuestionInstance.notifiedDate);
        copy.completedDate = this.dateUtils
            .convertLocalDateToServer(taskQuestionInstance.completedDate);
        return copy;
    }
}
