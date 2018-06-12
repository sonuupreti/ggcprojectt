import { Component, OnInit } from '@angular/core';
//import { MatInputModule, MatButtonModule } from '@angular/material';
//import { PROJECTS } from '../projects.service';
import { TimesheetService } from '../timesheet.service';

@Component({
    selector: 'jhi-current-timesheet-view',
    templateUrl: './current-timesheet-view.component.html',
    styleUrls: ['./current-timesheet-view.css']
})
export class CurrentTimesheetView implements OnInit {
    projects: Array<any>;

    currentDate;
    private weekObj: any = {}; // = { weekNo: 0, year: 0, startDate: 0, startMonth: '', projects: [], status: '' }; // WeekObj ;
    private months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    private daysOfWeek = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
    private daysInMonths = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    // timesheetData: any = {};
    leaveTypes = [];
    selectedLeave;
    isLeaveAdded = false;
    timesheetData = {
        resourceAllocationSummary: {
            resource: {
                key: '20001',

                value: 'Manoj Nautiyal'
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

                    proportion: 50,

                    customerTimeTracking: false
                },

                {
                    project: {
                        key: 'INV0000005',

                        value: 'FinalSelect'
                    },

                    projectType: {
                        key: 'INV',

                        value: 'Investment'
                    },

                    proportion: 50,

                    customerTimeTracking: true
                },

                {
                    project: {
                        key: 'PDL0000001',

                        value: 'Paid Leave'
                    },

                    projectType: {
                        key: 'PDL',

                        value: 'Paid Leave'
                    },

                    proportion: 100,

                    customerTimeTracking: false
                },

                {
                    project: {
                        key: 'UPL0000002',

                        value: 'Unpaid Leave'
                    },

                    projectType: {
                        key: 'UPL',

                        value: 'Unpaid Leave'
                    },

                    proportion: 100,

                    customerTimeTracking: false
                }
            ]
        },

        weekDetails: {
            weekStartDate: '2018-05-28',

            weekEndDate: '2018-06-03',

            weekLength: 7,

            weekStartDay: 'MONDAY',

            weekEndDay: 'SUNDAY',

            dailyStandardHours: 8,

            weeklyStandardHours: 40,

            flexibleWeekends: false,

            dailyDetails: [
                {
                    date: '2018-05-28',

                    day: 'MONDAY',

                    type: {
                        code: 'WD',

                        value: 'Regular Working Day'
                    },

                    remarks: null
                },

                {
                    date: '2018-05-29',

                    day: 'TUESDAY',

                    type: {
                        code: 'WD',

                        value: 'Regular Working Day'
                    },

                    remarks: null
                },

                {
                    date: '2018-05-30',

                    day: 'WEDNESDAY',

                    type: {
                        code: 'WD',

                        value: 'Regular Working Day'
                    },

                    remarks: null
                },

                {
                    date: '2018-05-31',

                    day: 'THURSDAY',

                    type: {
                        code: 'HD',

                        value: 'Holiday'
                    },

                    remarks: 'Diwali'
                },

                {
                    date: '2018-06-01',

                    day: 'FRIDAY',

                    type: {
                        code: 'WD',

                        value: 'Regular Working Day'
                    },

                    remarks: null
                },

                {
                    date: '2018-06-02',

                    day: 'SATURDAY',

                    type: {
                        code: 'WE',

                        value: 'Weekend'
                    },

                    remarks: null
                },

                {
                    date: '2018-06-03',

                    day: 'SUNDAY',

                    type: {
                        code: 'WE',

                        value: 'Weekend'
                    },

                    remarks: null
                }
            ]
        },

        actions: [
            {
                code: 'SAVE',

                description: 'Save Timesheet'
            },

            {
                code: 'SUBMIT',

                description: 'Submit Timesheet'
            }
        ]
    };

    constructor(private timesheetService: TimesheetService) {}

    ngOnInit() {
        this.createInitialTimesheetData();
        //this.currentDate = new Date();
        // this.timesheetService.getGetInitialTimesheet().subscribe(timesheetData => {
        //     this.timesheetData = timesheetData;
        //     // this.createInitialTimesheetData();
        // });
    }
    trackByIndex(index: number, obj: any): any {
        return index;
    }
    createInitialTimesheetData() {
        this.weekObj.projects = this.createProjectsModel(this.timesheetData.resourceAllocationSummary.projects);
        this.weekObj.daysData = this.createDaysData(this.timesheetData.weekDetails.dailyDetails);
        this.weekObj.status = 'NOT SUBMITTED';
        this.weekObj.actions = this.timesheetData.actions;
        this.weekObj.startDate = this.timesheetData.weekDetails.weekStartDate;
        this.weekObj.endDate = this.timesheetData.weekDetails.weekEndDate;
        this.weekObj.leaves = [];
    }

    createDaysData(daysData) {
        let daysViewModel = [];
        if (daysData.length > 0) {
            daysData.forEach(dayObj => {
                let dModel: any = {};
                let splittedDateObj = dayObj.date.split('-');
                dModel.date = splittedDateObj[2];
                dModel.month = this.months[parseInt(splittedDateObj[1]) - 1];
                dModel.day = dayObj.day.substr(0, 3);
                dModel.remarks = dayObj.remarks;
                dModel.code = dayObj.type.code;
                daysViewModel.push(dModel);
            });
        }
        return daysViewModel;
    }

    createProjectHoursPerDayModel(i) {
        let hoursPerDayModel = [];
        let comment = 'My timesheet project'; // keeping it fixed for now
        let currentProj = this.weekObj.projects;
        currentProj.forEach(proj => {
            let timeObj: any = {};
            timeObj.comments = proj.hours[i].comments;
            timeObj.hours = parseInt(proj.hours[i].hour);
            timeObj.projectCode = proj.code;
            hoursPerDayModel.push(timeObj);
        });
        return hoursPerDayModel;
    }

    createHourEntriesModel() {
        let comment = 'My timesheet Day'; // keeping it fixed for now
        let dailydetails = this.timesheetData.weekDetails.dailyDetails;
        let hourEntries = [];
        for (let i = 0; i < 7; i++) {
            let entryObj: any = {};
            entryObj.comments = comment;
            entryObj.date = dailydetails[i].date;
            entryObj.timeEntries = this.createProjectHoursPerDayModel(i);
            hourEntries.push(entryObj);
        }
        return hourEntries;
    }

    saveOrSubmit(action) {
        var totalHours = this.calculateTotalHours(this.weekObj.projects);
        var dailyEntries = this.createHourEntriesModel();
        var hoursValidation = false;
        if (action === 'SUBMIT' && totalHours < 40) {
            alert('Minimum 40 hours are reaquired per week');
        } else if (action === 'SUBMIT' && totalHours === 40) {
            let data = {
                action: action,
                resourceCode: '20001',
                week: {
                    dailyEntries: dailyEntries,
                    weekStartDate: this.timesheetData.weekDetails.dailyDetails[0].date
                },
                weeklyTimeSheetId: 10
            };
            this.timesheetService.saveSubmitTimesheet(data).subscribe(timesheetData => {
                //debugger;
                if (timesheetData) {
                    this.weekObj.status = timesheetData.status.code;
                    alert('Timesheet ' + this.weekObj.status + ' successfully');
                    //this.weekObj.actions = this.timesheetData.actions;
                }
            });
        } else if (action === 'SUBMIT' && totalHours > 40) {
            dailyEntries.forEach(function(data, index) {
                var commentValidation = dailyEntries[index].timeEntries;
                commentValidation.forEach(function(data, index) {
                    if (commentValidation[index].hours > 8 && data.comments.length > 10) {
                        hoursValidation = true;
                    }
                });
            });
            if (hoursValidation) {
                let data = {
                    action: action,
                    resourceCode: '20001',
                    week: {
                        dailyEntries: dailyEntries,
                        weekStartDate: this.timesheetData.weekDetails.dailyDetails[0].date
                    },
                    weeklyTimeSheetId: 10
                };
                this.timesheetService.saveSubmitTimesheet(data).subscribe(timesheetData => {
                    if (timesheetData) {
                        this.weekObj.status = timesheetData.status.code;
                        alert('Timesheet  with comments' + this.weekObj.status + ' successfully');
                        //this.weekObj.actions = this.timesheetData.actions;
                    }
                });
            }
        }
    }

    createProjectsModel(projects) {
        let projectsModel = [];
        if (projects.length > 0) {
            projects.forEach(project => {
                if (project.projectType.key != 'PDL' && project.projectType.key != 'UPL') {
                    let projObj = {
                        code: project.project.key,
                        name: project.project.key + ' - ' + project.project.value + ' ' + project.proportion + '%',
                        hours: [
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' }
                        ],
                        attachment: project.customerTimeTracking,
                        type: project.projectType.key
                    };
                    projectsModel.push(projObj);
                } else {
                    let projObj = {
                        code: project.project.key,
                        name: project.project.value,
                        hours: [
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' },
                            { hour: 0, comments: '' }
                        ],
                        type: project.projectType.key
                        //attachment: project.customerTimeTracking
                    };
                    this.leaveTypes.push(projObj);
                }
            });
        }
        return projectsModel;
    }

    calculateProjectHours(a) {
        let hours = 0;
        if (a && a.length > 0) {
            for (let i = 0; i < a.length; i++) {
                let h = parseInt(a[i].hour);
                if (isNaN(h)) {
                    hours += 0;
                } else {
                    hours += h;
                }
            }
        }
        return hours;
    }
    calculateDayHours(p, indx) {
        let hours = 0;
        if (p && p.length > 0) {
            for (let i = 0; i < p.length; i++) {
                let h = parseInt(p[i].hours[indx].hour);
                if (isNaN(h)) {
                    hours += 0;
                } else {
                    hours += h;
                }
            }
        }
        return hours;
    }
    calculateTotalHours(p) {
        let hours = 0;
        if (p && p.length > 0) {
            for (let i = 0; i < p.length; i++) {
                if (p[i].hours.length > 0) {
                    for (let j = 0; j < p[i].hours.length; j++) {
                        let h = parseInt(p[i].hours[j].hour);
                        if (isNaN(h)) {
                            hours += 0;
                        } else {
                            hours += h;
                        }
                    }
                }
            }
        }
        return hours;
    }

    selectLeave(event) {
        let leaveToAdd = this.leaveTypes.findIndex(l => {
            return l.name === event.source.value;
        });
        this.weekObj.projects.push(this.leaveTypes[leaveToAdd]);
        this.leaveTypes.splice(leaveToAdd, 1);
        this.isLeaveAdded = true;
        event.source.value = '';
        this.selectedLeave = '';
    }

    // getWeek calculates the week no. for the current week
    getWeek(d, dowOffset) {
        dowOffset = typeof dowOffset == 'number' ? dowOffset : 0; //default dowOffset to zero
        let newYear = new Date(d.getFullYear(), 0, 1);
        let day = newYear.getDay() - dowOffset; //the day of week the year begins on
        day = day >= 0 ? day : day + 7;
        let daynum =
            Math.floor((d.getTime() - newYear.getTime() - (d.getTimezoneOffset() - newYear.getTimezoneOffset()) * 60000) / 86400000) + 1;
        let weeknum;
        //if the year starts before the middle of a week
        if (day < 4) {
            weeknum = Math.floor((daynum + day - 1) / 7) + 1;
            if (weeknum > 52) {
                let nYear = new Date(d.getFullYear() + 1, 0, 1);
                let nday = nYear.getDay() - dowOffset;
                nday = nday >= 0 ? nday : nday + 7;
                /*if the next year starts before the middle of
	              the week, it is week #1 of that year*/
                weeknum = nday < 4 ? 1 : 53;
            }
        } else {
            weeknum = Math.floor((daynum + day - 1) / 7);
        }
        return weeknum;
    }

    clearLeave(project) {
        let leavesToRemove = this.weekObj.projects.findIndex(l => {
            return l.name === project.name;
        });
        this.weekObj.projects.splice(leavesToRemove, 1);
        this.leaveTypes.push(project);
        if (this.weekObj.projects.length === 2) {
            this.isLeaveAdded = false;
        }
    }
}
