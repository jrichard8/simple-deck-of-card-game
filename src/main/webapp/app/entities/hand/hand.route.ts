import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Hand } from 'app/shared/model/hand.model';
import { HandService } from './hand.service';
import { HandComponent } from './hand.component';
import { HandDetailComponent } from './hand-detail.component';
import { HandUpdateComponent } from './hand-update.component';
import { HandDeletePopupComponent } from './hand-delete-dialog.component';
import { IHand } from 'app/shared/model/hand.model';

@Injectable({ providedIn: 'root' })
export class HandResolve implements Resolve<IHand> {
    constructor(private service: HandService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Hand> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Hand>) => response.ok),
                map((hand: HttpResponse<Hand>) => hand.body)
            );
        }
        return of(new Hand());
    }
}

export const handRoute: Routes = [
    {
        path: 'hand',
        component: HandComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hands'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hand/:id/view',
        component: HandDetailComponent,
        resolve: {
            hand: HandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hands'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hand/new',
        component: HandUpdateComponent,
        resolve: {
            hand: HandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hands'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hand/:id/edit',
        component: HandUpdateComponent,
        resolve: {
            hand: HandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hands'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const handPopupRoute: Routes = [
    {
        path: 'hand/:id/delete',
        component: HandDeletePopupComponent,
        resolve: {
            hand: HandResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hands'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
