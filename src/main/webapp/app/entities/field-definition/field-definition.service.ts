import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { FieldDefinition } from './field-definition.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FieldDefinitionService {

    private resourceUrl = 'api/field-definitions';

    constructor(private http: Http) { }

    create(fieldDefinition: FieldDefinition): Observable<FieldDefinition> {
        const copy = this.convert(fieldDefinition);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(fieldDefinition: FieldDefinition): Observable<FieldDefinition> {
        const copy = this.convert(fieldDefinition);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<FieldDefinition> {
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

    private convert(fieldDefinition: FieldDefinition): FieldDefinition {
        const copy: FieldDefinition = Object.assign({}, fieldDefinition);
        return copy;
    }
}
