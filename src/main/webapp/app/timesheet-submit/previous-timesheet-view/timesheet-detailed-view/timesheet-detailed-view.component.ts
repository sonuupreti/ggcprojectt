import { Component, Input, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-timesheet-detailed-view',
    templateUrl: './timesheet-detailed-view.component.html'
})
export class TimesheetDetailedViewComponent implements OnInit {
    @Input() data: any;
    ngOnInit() {}
}
