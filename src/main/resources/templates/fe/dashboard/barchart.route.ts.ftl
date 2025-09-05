import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../core';
import { BarchartComponent } from './barchart.component';

export const barchartRoute: Route = {
    path: 'barchart',
    component: BarchartComponent,
    data: {
        authorities: [${authorities}],
        pageTitle: 'dashboard.barchart.home.title'
    },
    canActivate: [UserRouteAccessService]
};
