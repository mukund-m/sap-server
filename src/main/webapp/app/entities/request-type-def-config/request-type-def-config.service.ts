import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RequestTypeDefConfig } from './request-type-def-config.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RequestTypeDefConfigService {

    private resourceUrl = 'api/request-type-def-configs';

    constructor(private http: Http) { }

    create(requestTypeDefConfig: RequestTypeDefConfig): Observable<RequestTypeDefConfig> {
        const copy = this.convert(requestTypeDefConfig);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(requestTypeDefConfig: RequestTypeDefConfig): Observable<RequestTypeDefConfig> {
        const copy = this.convert(requestTypeDefConfig);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RequestTypeDefConfig> {
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

    private convert(requestTypeDefConfig: RequestTypeDefConfig): RequestTypeDefConfig {
        const copy: RequestTypeDefConfig = Object.assign({}, requestTypeDefConfig);
        return copy;
    }
}
