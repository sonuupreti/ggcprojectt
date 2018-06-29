import { Component, OnInit, ComponentFactoryResolver, ViewChildren, Type, QueryList } from '@angular/core';
import { DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs/Observable';
import { TimesheetDetailedViewComponent } from './timesheet-detailed-view/timesheet-detailed-view.component';
import { TimeSheetsDetailedViewDirective } from './timesheet-detailed-view/timesheet-detailed-view.directive';
import { TimesheetService } from './../timesheet.service';

@Component({
    selector: 'jhi-previous-timesheet-view',
    templateUrl: './previous-timesheet-view.component.html',
    styleUrls: ['./previous-timesheet-view.component.css']
})
export class PreviousTimesheetViewComponent implements OnInit {
    displayedColumns = ['weekOf', 'submittedOn', 'status', 'comments', 'quickLinks'];
    timesheets: any;
    isExpansionDetailRow = (i: number, row: Object) => row.hasOwnProperty('detailRow');
    expandedElement: any;
    dynamicComponentRef: any;
    timesheetsData: any = {};
    @ViewChildren(TimeSheetsDetailedViewDirective) detailedRows: QueryList<TimeSheetsDetailedViewDirective>;

    constructor(private componentFactoryResolver: ComponentFactoryResolver, private timesheetService: TimesheetService) {}

    ngOnInit() {
        this.timesheetService.getReturnTimesheets().subscribe(data => {
            this.timesheetsData = data;
            this.timesheets = new RecentTSDataSource(this.timesheetsData.timesheets);
        });
    }

    expandRow(ev, timesheet, index) {
        if (this.dynamicComponentRef) {
            this.dynamicComponentRef.destroy();
        }
        if (ev.currentTarget.classList.contains('expanded')) {
            this.expandedElement = undefined;
        } else {
            this.expandedElement = timesheet;
            this.loadComponent(index);
        }
    }

    loadComponent(index) {
        const componentData = this.getData();
        const componentFactory = this.componentFactoryResolver.resolveComponentFactory(componentData.component);
        let viewContainerRef;
        this.detailedRows.forEach(function(data, i) {
            if (i === index) {
                viewContainerRef = data.viewContainerRef;
            }
        });
        viewContainerRef.clear();
        this.dynamicComponentRef = viewContainerRef.createComponent(componentFactory);
        (<TimesheetDetailedViewComponent>this.dynamicComponentRef.instance).data = componentData.data;
    }

    getData() {
        return new DetailedRowData(TimesheetDetailedViewComponent, this.expandedElement);
    }
    getStatus(code) {
        if (code === 'PENDING_SUBMISSION') {
            return 'Not Submitted';
        } else if (code === 'SAVED') {
            return 'Saved';
        } else if (code === 'SUBMITTED') {
            return 'Submitted';
        } else if (code === 'APPROVED') {
            return 'Approved';
        } else if (code === 'REJECTED') {
            return 'Rejected';
        } else if (code === 'PARTIALLY_APPROVED_REJECTED') {
        } else if (code === 'PARTIALLY_APPROVED_PENDING') {
            return 'Partially Approved';
        } else if (code === 'PARTIALLY_REJECTED_PENDING') {
            return 'Partially Rejected;';
        }
    }
    getStatusColor(code) {
        if (code === 'SUBMITTED') {
            return 'apprvr-action-submitted';
        }
        if (code === 'APPROVED' || code === 'PARTIALLY_APPROVED_PENDING') {
            return 'apprvr-action-success';
        } else if (code === 'REJECTED' || code === 'PARTIALLY_REJECTED_PENDING') {
            return 'apprvr-action-danger';
        }
    }
}

export class RecentTSDataSource extends DataSource<any> {
    timesheets: any;
    constructor(timesheets) {
        super();
        this.timesheets = timesheets;
    }
    connect(): Observable<Element[]> {
        const rows = [];
        this.timesheets.forEach(element => rows.push(element, { detailRow: true, element }));
        return Observable.of(rows);
    }
    disconnect() {}
}

export class DetailedRowData {
    constructor(public component: Type<any>, public data: any) {}
}
