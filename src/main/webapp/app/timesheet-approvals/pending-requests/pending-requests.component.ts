import { Component, OnInit, Input } from '@angular/core';
import { TimesheetApprovalsService } from 'app/timesheet-approvals/timesheet-approvals.service';
import { NgbPopoverConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-pending-requests',
    templateUrl: './pending-requests.component.html',
    styleUrls: ['./pending-requests.component.scss']
})
export class PendingRequestsComponent implements OnInit {
    selected = 'RecentFirst';
    @Input() tabRequest: any;
    timeSheetData: any = [];
    pendingData: any = [
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
                totalWeekSubmitedHours: 41,
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
                            hours: 9,
                            comments: 'Spent time to investigate the issues related to admin module'
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
                            hours: 0,
                            comments: ''
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
                            hours: 0,
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
                        isApproved: false,
                        isRejected: false
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 25,
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
                                hours: 9,
                                comments: 'Worked on project ITRACK - recent timesheets module to displaying the recent timesheet data'
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                        isApproved: true,
                        isRejected: false
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 16,
                    dailyEntries: [
                        {
                            date: '2018-05-21',
                            timeEntries: {
                                hours: 0,
                                comments: 'Worked on recoureces allocation for the project in Itrack'
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
                                comments: 'Worked on Macys - My Account home page'
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 4,
                                comments: 'Worked on  Macys -  StarRewards loyality program'
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
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
                        isRejected: true
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 0,
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                key: '20005',
                value: 'Shamshuddin Shaik'
            },
            weekDetails: {
                weekStartDate: '2018-05-28',
                weekEndDate: '2018-06-03',
                submittedDate: '2018-05-27',
                weekLength: 7,
                week: 37,
                weekStartDay: 'MONDAY',
                weekEndDay: 'SUNDAY',
                dailyStandardHours: 8,
                weeklyStandardHours: 40,
                flexibleWeekends: false,
                totalWeekSubmitedHours: 42,
                dailyDetails: [
                    {
                        date: '2018-05-28',
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
                        date: '2018-05-29',
                        day: 'TUESDAY',
                        type: {
                            code: 'WD',
                            value: 'Regular Working Day'
                        },
                        remarks: null,
                        timeEntries: {
                            hours: 9,
                            comments: 'Worked on Projects module in Itrack Application'
                        }
                    },
                    {
                        date: '2018-05-30',
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
                        date: '2018-05-31',
                        day: 'THURSDAY',
                        type: {
                            code: 'HD',
                            value: 'Holiday'
                        },
                        remarks: 'Diwali',
                        timeEntries: {
                            hours: 9,
                            comments: 'Worked on Project ITRACk - recent timesheets with mock json response'
                        }
                    },
                    {
                        date: '2018-06-01',
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
                        date: '2018-06-02',
                        day: 'SATURDAY',
                        type: {
                            code: 'WE',
                            value: 'Weekend'
                        },
                        remarks: null,
                        timeEntries: {
                            hours: 0,
                            comments: ''
                        }
                    },
                    {
                        date: '2018-06-03',
                        day: 'SUNDAY',
                        type: {
                            code: 'WE',
                            value: 'Weekend'
                        },
                        remarks: null,
                        timeEntries: {
                            hours: 0,
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
                        isApproved: false,
                        isRejected: true
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 25,
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 5,
                                comments: 'Worked on Project ITRACk -  recent time sheets'
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
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                        isRejected: false
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 9,
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
                                hours: 1,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 4,
                                comments: 'Worked on Project ITRACk - resouce allocation with mock response'
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 4,
                                comments: 'Worked on Project ITRACk -  project allocation to the resouce'
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
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
                        isRejected: true
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 8,
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 8,
                                comments: 'PTO- due to personal work'
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        }
                    ]
                }
            ]
        }
    ];
    approvedData: any = [
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
                totalWeekSubmitedHours: 41,
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
                            hours: 9,
                            comments: 'Worked on Project ITRACk - admin module'
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
                            hours: 0,
                            comments: ''
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
                            hours: 0,
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
                        isRejected: false
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 25,
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
                                hours: 9,
                                comments: 'Worked on Project ITRACk - admin module with mock response'
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                        isApproved: true,
                        isRejected: false
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 16,
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
                                comments: 'Worked on Project ITRACk - worked on the services for project re allocation'
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 4,
                                comments: 'Worked on Project ITRACk - worked on managing resources for reporting manager'
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
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
                        isApproved: true,
                        isRejected: false
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 0,
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                key: '20005',
                value: 'Shamshuddin Shaik'
            },
            weekDetails: {
                weekStartDate: '2018-05-28',
                weekEndDate: '2018-06-03',
                submittedDate: '2018-05-27',
                weekLength: 7,
                week: 37,
                weekStartDay: 'MONDAY',
                weekEndDay: 'SUNDAY',
                dailyStandardHours: 8,
                weeklyStandardHours: 40,
                flexibleWeekends: false,
                totalWeekSubmitedHours: 42,
                dailyDetails: [
                    {
                        date: '2018-05-28',
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
                        date: '2018-05-29',
                        day: 'TUESDAY',
                        type: {
                            code: 'WD',
                            value: 'Regular Working Day'
                        },
                        remarks: null,
                        timeEntries: {
                            hours: 9,
                            comments: 'Worked on Project ITRACk - data submission part'
                        }
                    },
                    {
                        date: '2018-05-30',
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
                        date: '2018-05-31',
                        day: 'THURSDAY',
                        type: {
                            code: 'HD',
                            value: 'Holiday'
                        },
                        remarks: 'Diwali',
                        timeEntries: {
                            hours: 9,
                            comments: 'Worked on Project ITRACk - Comments section part'
                        }
                    },
                    {
                        date: '2018-06-01',
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
                        date: '2018-06-02',
                        day: 'SATURDAY',
                        type: {
                            code: 'WE',
                            value: 'Weekend'
                        },
                        remarks: null,
                        timeEntries: {
                            hours: 0,
                            comments: ''
                        }
                    },
                    {
                        date: '2018-06-03',
                        day: 'SUNDAY',
                        type: {
                            code: 'WE',
                            value: 'Weekend'
                        },
                        remarks: null,
                        timeEntries: {
                            hours: 0,
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
                        isRejected: false
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 26,
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
                                hours: 9,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 5,
                                comments: 'Worked on Project ITRACk - Key clock authentication'
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
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                        isApproved: true,
                        isRejected: false
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 8,
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 4,
                                comments: 'Worked on Project ITRACk - implementing the services for resource allocation'
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 4,
                                comments: 'Worked on Project ITRACk - developing the database for itrack'
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
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
                        isApproved: true,
                        isRejected: false
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 8,
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 8,
                                comments: 'Worked on Project ITRACk - worked on pending approvals timesheet'
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        }
                    ]
                }
            ]
        }
    ];
    rejectedData: any = [
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
                totalWeekSubmitedHours: 41,
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
                            hours: 9,
                            comments: 'Worked on Project ITRACk -  approved time sheet requests'
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
                            hours: 0,
                            comments: ''
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
                            hours: 0,
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
                        isApproved: false,
                        isRejected: true
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 25,
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
                                hours: 9,
                                comments: 'Worked on Project ITRACk - REjected time sheet requests'
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                        isRejected: true
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 16,
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
                                comments: 'Worked on Project ITRACk - implementing the get services for pending approvals'
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 4,
                                comments: 'Worked on Project ITRACk -  developing the post service for approving the timesheet'
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
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
                        isRejected: true
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 0,
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                key: '20005',
                value: 'Shamshuddin Shaik'
            },
            weekDetails: {
                weekStartDate: '2018-05-28',
                weekEndDate: '2018-06-03',
                submittedDate: '2018-05-27',
                weekLength: 7,
                week: 37,
                weekStartDay: 'MONDAY',
                weekEndDay: 'SUNDAY',
                dailyStandardHours: 8,
                weeklyStandardHours: 40,
                flexibleWeekends: false,
                totalWeekSubmitedHours: 41,
                dailyDetails: [
                    {
                        date: '2018-05-28',
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
                        date: '2018-05-29',
                        day: 'TUESDAY',
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
                        date: '2018-05-30',
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
                        date: '2018-05-31',
                        day: 'THURSDAY',
                        type: {
                            code: 'HD',
                            value: 'Holiday'
                        },
                        remarks: 'Diwali',
                        timeEntries: {
                            hours: 9,
                            comments:
                                'Worked on Project ITRACk - creating the json formatted structure which suits for the resource allocation'
                        }
                    },
                    {
                        date: '2018-06-01',
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
                        date: '2018-06-02',
                        day: 'SATURDAY',
                        type: {
                            code: 'WE',
                            value: 'Weekend'
                        },
                        remarks: null,
                        timeEntries: {
                            hours: 0,
                            comments: ''
                        }
                    },
                    {
                        date: '2018-06-03',
                        day: 'SUNDAY',
                        type: {
                            code: 'WE',
                            value: 'Weekend'
                        },
                        remarks: null,
                        timeEntries: {
                            hours: 0,
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
                        isApproved: false,
                        isRejected: true
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 25,
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 5,
                                comments: 'Worked on Project ITRACk - pending requests module week filter implementation'
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
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
                        isRejected: true
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 8,
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 4,
                                comments: 'Worked on Project ITRACk -  week wise filter for approved timesheets'
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 4,
                                comments: 'Worked on Project ITRACk - week wise filter reject timesheets'
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
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
                        isRejected: true
                    },
                    proportion: 50,
                    customerTimeTracking: false,
                    totalSubmittedHrs: 8,
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
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-23',
                            timeEntries: {
                                hours: 8,
                                comments: 'Worked on Project ITRACk - displaying comment for all the timesheets'
                            }
                        },
                        {
                            date: '2018-05-24',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-25',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-26',
                            timeEntries: {
                                hours: 0,
                                comments: ''
                            }
                        },
                        {
                            date: '2018-05-27',
                            timeEntries: {
                                hours: 0,
                                comments: ''
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
        if (this.tabRequest === 'pending') {
            this.timeSheetData = this.pendingData;
        } else if (this.tabRequest === 'approved') {
            this.timeSheetData = this.approvedData;
        } else if (this.tabRequest === 'rejected') {
            this.timeSheetData = this.rejectedData;
        }
    }

    public getColor(projectKey) {
        if (projectKey === 'INV0000001') {
            return '#e8d23347';
        }
    }
    public getActions(projectKey) {
        if (projectKey === 'INV0000001') {
            return false;
        }
        return true;
    }
}
