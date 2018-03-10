import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { RequestAttachment } from './request-attachment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RequestAttachmentService {

    private resourceUrl = 'api/request-attachments';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(requestAttachment: RequestAttachment): Observable<RequestAttachment> {
        const copy = this.convert(requestAttachment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(requestAttachment: RequestAttachment): Observable<RequestAttachment> {
        const copy = this.convert(requestAttachment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<RequestAttachment> {
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
        entity.uploadedOn = this.dateUtils
            .convertLocalDateFromServer(entity.uploadedOn);
    }

    private convert(requestAttachment: RequestAttachment): RequestAttachment {
        const copy: RequestAttachment = Object.assign({}, requestAttachment);
        copy.uploadedOn = this.dateUtils
            .convertLocalDateToServer(requestAttachment.uploadedOn);
        return copy;
    }
}
