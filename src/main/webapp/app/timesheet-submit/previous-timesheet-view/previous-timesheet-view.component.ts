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
    displayedColumns = ['weekOf', 'status', 'submittedOn', 'approverAction', 'comments', 'quickLinks'];
    timesheets = new RecentTSDataSource();
    isExpansionDetailRow = (i: number, row: Object) => row.hasOwnProperty('detailRow');
    expandedElement: any;
    dynamicComponentRef: any;
    @ViewChildren(TimeSheetsDetailedViewDirective) detailedRows: QueryList<TimeSheetsDetailedViewDirective>;

    constructor(private componentFactoryResolver: ComponentFactoryResolver, private timesheetService: TimesheetService) {}

    ngOnInit() {
        // this.timesheetService.getReturnTimesheets.subscribe(data);
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
}
const timesheets = [
    {
        status: 'Not Submitted',
        approverAction: 'N/A',
        approverComments: 'N/A',
        resource: {
            key: '20001',
            value: 'Manoj Nautiyal'
        },
        weekDetails: {
            weekStartDate: '2018-05-21',
            weekEndDate: '2018-05-27',
            submittedDate: '2018-05-27',
            weekLength: 7,
            week: 36,
            weekStartDay: 'MONDAY',
            weekEndDay: 'SUNDAY',
            dailyStandardHours: 8,
            weeklyStandardHours: 40,
            flexibleWeekends: false,
            totalWeekSubmitedHours: 60,
            dailyDetails: [
                {
                    date: '2018-05-21',
                    day: 'MONDAY',
                    type: {
                        code: 'WD',
                        value: 'Regular Working Day'
                    },
                    remarks: null,
                    timeEntries: {
                        hours: 8,
                        comments: 'iTrack standup'
                    }
                },
                {
                    date: '2018-05-22',
                    day: 'TUESDAY',
                    type: {
                        code: 'WD',
                        value: 'Regular Working Day'
                    },
                    remarks: null,
                    timeEntries: {
                        hours: 8,
                        comments: 'iTrack standup'
                    }
                },
                {
                    date: '2018-05-23',
                    day: 'WEDNESDAY',
                    type: {
                        code: 'WD',
                        value: 'Regular Working Day'
                    },
                    remarks: null,
                    timeEntries: {
                        hours: 8,
                        comments: 'iTrack standup'
                    }
                },
                {
                    date: '2018-05-24',
                    day: 'THURSDAY',
                    type: {
                        code: 'HD',
                        value: 'Holiday'
                    },
                    remarks: 'Diwali',
                    timeEntries: {
                        hours: 8,
                        comments: 'iTrack standup'
                    }
                },
                {
                    date: '2018-05-25',
                    day: 'FRIDAY',
                    type: {
                        code: 'WD',
                        value: 'Regular Working Day'
                    },
                    remarks: null,
                    timeEntries: {
                        hours: 8,
                        comments: 'iTrack standup'
                    }
                },
                {
                    date: '2018-05-26',
                    day: 'SATURDAY',
                    type: {
                        code: 'WE',
                        value: 'Weekend'
                    },
                    remarks: null,
                    timeEntries: {
                        hours: 8,
                        comments: 'iTrack standup'
                    }
                },
                {
                    date: '2018-05-27',
                    day: 'SUNDAY',
                    type: {
                        code: 'WE',
                        value: 'Weekend'
                    },
                    remarks: null,
                    timeEntries: {
                        hours: 8,
                        comments: 'iTrack standup'
                    }
                }
            ]
        },
        projects: [
            {
                project: {
                    key: 'INV0000004',
                    value: 'iTrack'
                },
                projectType: {
                    key: 'INV',
                    value: 'Investment'
                },
                actions: {
                    isApproved: true,
                    isCancelled: false
                },
                proportion: 50,
                customerTimeTracking: false,
                totalSubmittedHrs: 20,
                dailyEntries: [
                    {
                        date: '2018-05-21',
                        timeEntries: {
                            hours: 8,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-22',
                        timeEntries: {
                            hours: 8,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-23',
                        timeEntries: {
                            hours: 0,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-24',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-25',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-26',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-27',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    }
                ]
            },
            {
                project: {
                    key: 'INV0000005',
                    value: 'sample'
                },
                projectType: {
                    key: 'INV',
                    value: 'Investment'
                },
                actions: {
                    isApproved: false,
                    isCancelled: true
                },
                proportion: 50,
                customerTimeTracking: false,
                totalSubmittedHrs: 20,
                dailyEntries: [
                    {
                        date: '2018-05-21',
                        timeEntries: {
                            hours: 0,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-22',
                        timeEntries: {
                            hours: 0,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-23',
                        timeEntries: {
                            hours: 8,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-24',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-25',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-26',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-27',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    }
                ]
            },
            {
                project: {
                    key: 'INV0000001',
                    value: 'UNPAID LEAVE'
                },
                projectType: {
                    key: 'INV',
                    value: 'Investment'
                },
                actions: {
                    isApproved: false,
                    isCancelled: true
                },
                proportion: 50,
                customerTimeTracking: false,
                totalSubmittedHrs: 20,
                dailyEntries: [
                    {
                        date: '2018-05-21',
                        timeEntries: {
                            hours: 0,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-22',
                        timeEntries: {
                            hours: 0,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-23',
                        timeEntries: {
                            hours: 8,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-24',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-25',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-26',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    },
                    {
                        date: '2018-05-27',
                        timeEntries: {
                            hours: 4,
                            comments: 'iTrack standup'
                        }
                    }
                ]
            }
        ]
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

export class DetailedRowData {
    constructor(public component: Type<any>, public data: any) {}
}
