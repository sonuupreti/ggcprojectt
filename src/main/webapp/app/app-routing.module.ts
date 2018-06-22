import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { ListAccountsComponent } from './accounts/list-accounts/list-accounts.component';
import { AddAccountComponent } from './accounts/add-account/add-account.component';
import { ViewAccountComponent } from './accounts/view-account/view-account.component';

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
                    path: 'resource',
                    loadChildren: './resource/resource.module#ResourceModule'
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
                    loadChildren: './timesheet-submit/timesheet-submit.module#TimeSheetSubmitModule',
                    data: {
                        authorities: ['ROLE_ADMIN']
                    }
                },
                {
                    path: 'timesheet-approvals',
                    loadChildren: './timesheet-approvals/timesheet-approvals.module#TimesheetApprovalsModule',
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
