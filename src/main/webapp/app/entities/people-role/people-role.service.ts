import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PeopleRole } from './people-role.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PeopleRoleService {

    private resourceUrl = 'api/people-roles';

    constructor(private http: Http) { }

    create(peopleRole: PeopleRole): Observable<PeopleRole> {
        const copy = this.convert(peopleRole);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(peopleRole: PeopleRole): Observable<PeopleRole> {
        const copy = this.convert(peopleRole);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PeopleRole> {
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

    private convert(peopleRole: PeopleRole): PeopleRole {
        const copy: PeopleRole = Object.assign({}, peopleRole);
        return copy;
    }
}
