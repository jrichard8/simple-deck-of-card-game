import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISuit } from 'app/shared/model/suit.model';

type EntityResponseType = HttpResponse<ISuit>;
type EntityArrayResponseType = HttpResponse<ISuit[]>;

@Injectable({ providedIn: 'root' })
export class SuitService {
    public resourceUrl = SERVER_API_URL + 'api/suits';

    constructor(private http: HttpClient) {}

    create(suit: ISuit): Observable<EntityResponseType> {
        return this.http.post<ISuit>(this.resourceUrl, suit, { observe: 'response' });
    }

    update(suit: ISuit): Observable<EntityResponseType> {
        return this.http.put<ISuit>(this.resourceUrl, suit, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISuit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISuit[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
