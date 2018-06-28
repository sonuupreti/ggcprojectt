import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-timesheet-view',
    templateUrl: './timesheet-view.component.html',
    styleUrls: ['./timesheet-view.component.css']
})
export class TimesheetViewComponent implements OnInit {
    data: any = {};
    constructor() {}

    ngOnInit() {}

    getOutputVal(status) {
        if (status) {
            this.data = status;
        }
    }
}
