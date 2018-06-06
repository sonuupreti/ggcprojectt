import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../material.module';

import { routes } from './timesheet-approvals-routing.module';

import { PendingRequestsComponent } from './pending-requests/pending-requests.component';
import { ApprovedRequestsComponent } from './approved-requests/approved-requests.component';
import { RejectedRequestsComponent } from './rejected-requests/rejected-requests.component';
import { TimesheetApprovalsComponent } from 'app/timesheet-approvals/timesheet-approvals.component';

@NgModule({
    imports: [CommonModule, MaterialModule, RouterModule.forChild(routes)],
    declarations: [TimesheetApprovalsComponent, PendingRequestsComponent, ApprovedRequestsComponent, RejectedRequestsComponent]
})
export class TimesheetApprovalsModule {}
