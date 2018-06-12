import { Component, Input, OnInit } from '@angular/core';
@Component({
    selector: 'jhi-timesheet-detailed-view',
    templateUrl: './timesheet-detailed-view.component.html',
    styleUrls: ['./timesheet-detailed-view.component.css']
})
export class TimesheetDetailedViewComponent {
    @Input() data: any;
}
