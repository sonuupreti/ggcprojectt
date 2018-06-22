import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../material.module';

import { routes } from './timesheet-approvals-routing.module';

import { PendingRequestsComponent } from './pending-requests/pending-requests.component';
import { ApprovedRequestsComponent } from './approved-requests/approved-requests.component';
import { RejectedRequestsComponent } from './rejected-requests/rejected-requests.component';
import { TimesheetApprovalsComponent } from 'app/timesheet-approvals/timesheet-approvals.component';
import { TimesheetApprovalsService } from 'app/timesheet-approvals/timesheet-approvals.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    imports: [CommonModule, MaterialModule, RouterModule.forChild(routes), NgbModule.forRoot()],
    declarations: [TimesheetApprovalsComponent, PendingRequestsComponent, ApprovedRequestsComponent, RejectedRequestsComponent],
    providers: [TimesheetApprovalsService]
})
export class TimesheetApprovalsModule {}
