import { Component, OnInit } from '@angular/core';
import { TimesheetApprovalsService } from 'app/timesheet-approvals/timesheet-approvals.service';

@Component({
    selector: 'jhi-pending-requests',
    templateUrl: './pending-requests.component.html',
    styleUrls: ['./pending-requests.component.css']
})
export class PendingRequestsComponent implements OnInit {
    selected = 'RecentFirst';
    timeSheetData = [];
    constructor(private timesheetApprovalService: TimesheetApprovalsService) {}

    ngOnInit() {
        this.timesheetApprovalService.getPendingTimesheetData().subscribe(timesheetData => {
            this.timeSheetData = timesheetData;
            console.log(this.timeSheetData);
        });
    }
}
