import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { FieldChoiceDefinition } from './field-choice-definition.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FieldChoiceDefinitionService {

    private resourceUrl = 'api/field-choice-definitions';

    constructor(private http: Http) { }

    create(fieldChoiceDefinition: FieldChoiceDefinition): Observable<FieldChoiceDefinition> {
        const copy = this.convert(fieldChoiceDefinition);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(fieldChoiceDefinition: FieldChoiceDefinition): Observable<FieldChoiceDefinition> {
        const copy = this.convert(fieldChoiceDefinition);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<FieldChoiceDefinition> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(fieldChoiceDefinition: FieldChoiceDefinition): FieldChoiceDefinition {
        const copy: FieldChoiceDefinition = Object.assign({}, fieldChoiceDefinition);
        return copy;
    }
}
