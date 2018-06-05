import { Component, OnInit } from '@angular/core';
import { DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'jhi-previous-timesheet-view',
    templateUrl: './previous-timesheet-view.component.html',
    styleUrls: ['./previous-timesheet-view.component.css']
})
export class PreviousTimesheetView {
    displayedColumns = ['weekOf', 'status', 'submittedOn', 'approverAction', 'comments', 'quickLinks'];
    timesheets = new RecentTSDataSource();
    isExpansionDetailRow = (i: number, row: Object) => row.hasOwnProperty('detailRow');
    expandedElement: any;

    expandRow(ev, timesheet) {
        if (ev.currentTarget.classList.contains('expanded')) {
            this.expandedElement = undefined;
        } else {
            this.expandedElement = timesheet;
        }
    }
}

const timesheets = [
    {
        weekOf: 'May 7th - 13th',
        status: 'Not Submitted',
        submittedOn: 'NA',
        approverAction: 'N/A',
        comments: 'N/A',
        details: [
            {
                projectID: 'IND000-iTracker',
                totalHrs: 20,
                days: [
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 6 },
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 5 },
                    { date: 'dd-mm-yyyy', workedHours: 0 },
                    { date: 'dd-mm-yyyy', workedHours: 0 }
                ],
                approverStatus: 'Approved'
            },
            {
                projectID: 'IND000-iTracker',
                totalHrs: 23,
                days: [
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 6 },
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 5 },
                    { date: 'dd-mm-yyyy', workedHours: 0 },
                    { date: 'dd-mm-yyyy', workedHours: 0 }
                ],
                approverStatus: 'declined'
            }
        ]
    },
    {
        weekOf: 'May 1st - 6th',
        status: 'Open',
        submittedOn: '05/06/2018',
        approverAction: 'Rejected',
        comments: 'Clients timesheet not matching',
        details: [
            {
                projectID: 'IND000-bcom',
                totalHrs: 20,
                days: [
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 6 },
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 5 },
                    { date: 'dd-mm-yyyy', workedHours: 0 },
                    { date: 'dd-mm-yyyy', workedHours: 0 }
                ],
                approverStatus: 'Approved'
            },
            {
                projectID: 'IND000-cms',
                totalHrs: 23,
                days: [
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 6 },
                    { date: 'dd-mm-yyyy', workedHours: 4 },
                    { date: 'dd-mm-yyyy', workedHours: 5 },
                    { date: 'dd-mm-yyyy', workedHours: 0 },
                    { date: 'dd-mm-yyyy', workedHours: 0 }
                ],
                approverStatus: 'declined'
            }
        ]
    },
    {
        weekOf: 'Apr 23rd - 30th',
        status: 'Submitted',
        submittedOn: '04/29/2018',
        approverAction: 'Partially Approved',
        comments: 'NA'
    },
    {
        weekOf: 'Apr 16th - 22nd',
        status: 'Submitted',
        submittedOn: '04/29/2018',
        approverAction: 'Rejected',
        comments: 'Extra login hours'
    }
];

export class RecentTSDataSource extends DataSource<any> {
    connect(): Observable<Element[]> {
        const rows = [];
        timesheets.forEach(element => rows.push(element, { detailRow: true, element }));
        return Observable.of(rows);
    }
    disconnect() {}
}
