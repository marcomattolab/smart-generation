import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../core';
import { RadarchartComponent } from './radarchart.component';

export const radarchartRoute: Route = {
    path: 'radarchart',
    component: RadarchartComponent,
    data: {
        authorities: [${authorities}],
        pageTitle: 'dashboard.radarchart.home.title'
    },
    canActivate: [UserRouteAccessService]
};
