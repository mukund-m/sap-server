import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { FieldOptionDefinition } from './field-option-definition.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FieldOptionDefinitionService {

    private resourceUrl = 'api/field-option-definitions';

    constructor(private http: Http) { }

    create(fieldOptionDefinition: FieldOptionDefinition): Observable<FieldOptionDefinition> {
        const copy = this.convert(fieldOptionDefinition);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(fieldOptionDefinition: FieldOptionDefinition): Observable<FieldOptionDefinition> {
        const copy = this.convert(fieldOptionDefinition);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<FieldOptionDefinition> {
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

    private convert(fieldOptionDefinition: FieldOptionDefinition): FieldOptionDefinition {
        const copy: FieldOptionDefinition = Object.assign({}, fieldOptionDefinition);
        return copy;
    }
}
