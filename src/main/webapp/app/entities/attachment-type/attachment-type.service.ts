import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AttachmentType } from './attachment-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AttachmentTypeService {

    private resourceUrl = 'api/attachment-types';

    constructor(private http: Http) { }

    create(attachmentType: AttachmentType): Observable<AttachmentType> {
        const copy = this.convert(attachmentType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(attachmentType: AttachmentType): Observable<AttachmentType> {
        const copy = this.convert(attachmentType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AttachmentType> {
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

    private convert(attachmentType: AttachmentType): AttachmentType {
        const copy: AttachmentType = Object.assign({}, attachmentType);
        return copy;
    }
}
