import { Routes } from '@angular/router';

import { listProjectsRoute } from './list-projects/list-projects.route';
import { addProjectRout } from './add-project/add-project.route';
import { viewProjectRoute } from './view-project/view-project.route';

const PROJECTS_ROUTES = [listProjectsRoute, addProjectRout, viewProjectRoute];

export const projectsState: Routes = [
    {
        path: '',
        children: PROJECTS_ROUTES,
        data: {
            authorities: ['ROLE_ADMIN']
        }
    }
];
