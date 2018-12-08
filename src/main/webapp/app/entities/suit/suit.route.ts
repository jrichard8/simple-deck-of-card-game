import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Suit } from 'app/shared/model/suit.model';
import { SuitService } from './suit.service';
import { SuitComponent } from './suit.component';
import { SuitDetailComponent } from './suit-detail.component';
import { SuitUpdateComponent } from './suit-update.component';
import { SuitDeletePopupComponent } from './suit-delete-dialog.component';
import { ISuit } from 'app/shared/model/suit.model';

@Injectable({ providedIn: 'root' })
export class SuitResolve implements Resolve<ISuit> {
    constructor(private service: SuitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Suit> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Suit>) => response.ok),
                map((suit: HttpResponse<Suit>) => suit.body)
            );
        }
        return of(new Suit());
    }
}

export const suitRoute: Routes = [
    {
        path: 'suit',
        component: SuitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'suit/:id/view',
        component: SuitDetailComponent,
        resolve: {
            suit: SuitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'suit/new',
        component: SuitUpdateComponent,
        resolve: {
            suit: SuitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suits'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'suit/:id/edit',
        component: SuitUpdateComponent,
        resolve: {
            suit: SuitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suits'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const suitPopupRoute: Routes = [
    {
        path: 'suit/:id/delete',
        component: SuitDeletePopupComponent,
        resolve: {
            suit: SuitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
