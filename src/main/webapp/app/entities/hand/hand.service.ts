import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHand } from 'app/shared/model/hand.model';

type EntityResponseType = HttpResponse<IHand>;
type EntityArrayResponseType = HttpResponse<IHand[]>;

@Injectable({ providedIn: 'root' })
export class HandService {
    public resourceUrl = SERVER_API_URL + 'api/hands';

    constructor(private http: HttpClient) {}

    create(hand: IHand): Observable<EntityResponseType> {
        return this.http.post<IHand>(this.resourceUrl, hand, { observe: 'response' });
    }

    update(hand: IHand): Observable<EntityResponseType> {
        return this.http.put<IHand>(this.resourceUrl, hand, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHand>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHand[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
