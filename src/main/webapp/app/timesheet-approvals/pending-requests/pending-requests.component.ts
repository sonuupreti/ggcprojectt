import { Component, OnInit, Input } from '@angular/core';
import { TimesheetApprovalsService } from 'app/timesheet-approvals/timesheet-approvals.service';
import { NgbPopoverConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-pending-requests',
    templateUrl: './pending-requests.component.html',
    styleUrls: ['./pending-requests.component.css']
})
export class PendingRequestsComponent implements OnInit {
    selected = 'RecentFirst';
    @Input() tabRequest: any;
    timeSheetData: any = [
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
                            comments: ''
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
                            comments: ''
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
                            comments: ''
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
                            comments: ''
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
                            comments: ''
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
                                comments: ''
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 4,
                                comments: ''
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 8,
                                comments: ''
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
                                comments: ''
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
                                comments: ''
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
                                comments: ''
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 4,
                                comments: ''
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
        },
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
                            comments: ''
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
                            comments: ''
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
                            comments: ''
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
                            comments: ''
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-22',
                            timeEntries: {
                                hours: 8,
                                comments: ''
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 4,
                                comments: ''
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
                                comments: ''
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
                                comments: ''
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
                                comments: ''
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
                                comments: ''
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
    constructor(private timesheetApprovalService: TimesheetApprovalsService, private config: NgbPopoverConfig) {
        config.placement = 'top';
        // config.triggers = 'click';
        config.triggers = 'hover';
    }

    ngOnInit() {
        console.log(this.tabRequest);
    }

    public getColor(projectKey) {
        if (projectKey === 'INV0000001') {
            return '#e8d23347';
        }
    }
}
