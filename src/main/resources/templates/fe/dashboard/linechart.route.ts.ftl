import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../core';
import { LinechartComponent } from './linechart.component';

export const linechartRoute: Route = {
    path: 'linechart',
    component: LinechartComponent,
    data: {
        authorities: [${authorities}],
        pageTitle: 'dashboard.linechart.home.title'
    },
    canActivate: [UserRouteAccessService]
};
