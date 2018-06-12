import { Route } from '@angular/router';

import { ViewResourceComponent } from './view-resource.component';

export const viewResourceRout: Route = {
    path: 'view-resource/:resourceCode',
    component: ViewResourceComponent
};
