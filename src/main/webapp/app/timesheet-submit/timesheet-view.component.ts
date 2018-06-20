import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-timesheet-view',
    templateUrl: './timesheet-view.component.html',
    styleUrls: ['./timesheet-view.component.css']
})
export class TimesheetViewComponent implements OnInit {
    submitted: boolean = false;
    constructor() {}

    ngOnInit() {}

    getOutputVal(status) {
        if (status) {
            this.submitted = status;
        }
    }
}
