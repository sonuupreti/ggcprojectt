import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { ListAccountsComponent } from './accounts/list-accounts/list-accounts.component';
import { AddAccountComponent } from './accounts/add-account/add-account.component';
import { ViewAccountComponent } from './accounts/view-account/view-account.component';
import { TimesheetView } from './timesheet-view/timesheet-view.component';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                ...LAYOUT_ROUTES,
                {
                    path: 'admin',
                    loadChildren: './admin/admin.module#Itrack2AdminModule'
                },
                {
                    path: 'projects',
                    loadChildren: './projects/projects.module#ProjectsModule'
                },
                {
                    path: 'listaccounts',
                    component: ListAccountsComponent
                },
                {
                    path: 'addaccount',
                    component: AddAccountComponent
                },
                {
                    path: 'view-account/:accountCode',
                    component: ViewAccountComponent
                },
                {
                    path: 'timesheet-view',
                    component: TimesheetView,
                    data: {
                        authorities: ['ROLE_ADMIN']
                    }
                }
            ],
            { useHash: true, enableTracing: DEBUG_INFO_ENABLED }
        )
    ],
    exports: [RouterModule]
})
export class Itrack2AppRoutingModule {}
