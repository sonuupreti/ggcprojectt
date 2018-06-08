import { Route } from '@angular/router';

import { ViewProjectComponent } from '../view-project/view-project.component';

export const viewProjectRoute: Route = {
    path: 'view-project/:projectCode',
    component: ViewProjectComponent
};
