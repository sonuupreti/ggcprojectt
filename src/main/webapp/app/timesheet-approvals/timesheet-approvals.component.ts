import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-timesheet-approvals',
    templateUrl: './timesheet-approvals.component.html',
    styles: []
})
export class TimesheetApprovalsComponent implements OnInit {
    pending = 'pending';
    approved = 'approved';
    rejected = 'rejected';
    constructor() {}

    ngOnInit() {}
}
