import { Route } from '@angular/router';
import { ListProjectsComponent } from './list-projects.component';

export const listProjectsRoute: Route = {
    path: 'list-projects',
    component: ListProjectsComponent,
    data: {
        pageTitle: 'Projects'
    }
};
