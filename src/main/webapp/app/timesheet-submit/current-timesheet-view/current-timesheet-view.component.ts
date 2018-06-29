import { Component, OnInit, ViewChildren, HostListener, EventEmitter, Output, QueryList } from '@angular/core';
import { TimesheetService } from '../timesheet.service';
import { NgbPopover } from '@ng-bootstrap/ng-bootstrap';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { DialogConfirmBodyComponent } from './dialog-confirm-body/dialog-confirm-body.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'jhi-current-timesheet-view',
    templateUrl: './current-timesheet-view.component.html',
    styleUrls: ['./current-timesheet-view.css'],
    entryComponents: [DialogConfirmBodyComponent]
})
export class CurrentTimesheetView implements OnInit {
    projects: Array<any>;
    currentDate;
    private weekObj: any = {};
    private months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    leaveTypes = [];
    selectedLeave;
    isLeaveAdded = false;
    onSubmit: boolean = false;
    @ViewChildren('p') public popoverList: QueryList<NgbPopover>;
    @Output() submittedToParent = new EventEmitter<boolean>();
    timesheetData: any = {};

    constructor(
        private timesheetService: TimesheetService,
        public dialog: MatDialog,
        private route: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.getTimeSheetData();
    }
    getTimeSheetData(date?) {
        if (date) {
            this.leaveTypes = [];
        }
        this.timesheetService.getTimesheetData(date).subscribe(timesheetData => {
            this.timesheetData = this.parseModel(timesheetData);
            this.createInitialTimesheetData();
        });
    }
    getTimeSheetDataById(id) {
        this.leaveTypes = [];
        this.timesheetService.getTimeSheetDataById(id).subscribe(timesheetData => {
            this.timesheetData = this.parseModel(timesheetData);
            this.createInitialTimesheetData();
        });
    }
    parseModel(timesheetData) {
        if (timesheetData.timesheetStatus.code === 'PENDING_SUBMISSION') {
            this.weekObj.status = 'NOT SUBMITTED';
        } else if (timesheetData.timesheetStatus.code === 'SUBMITTED') {
            this.weekObj.status = 'SUBMITTED';
        } else if (timesheetData.timesheetStatus.code === 'SAVED') {
            this.weekObj.status = 'SAVED';
        }
        const timeEntries = {
            hours: '',
            comments: ''
        };
        if (this.weekObj.status === 'NOT SUBMITTED' || this.weekObj.status === 'SAVED') {
            let dailyEntries = [];
            let obj = {
                date: '',
                comments: '',
                timeEntries: timeEntries
            };
            timesheetData.weekDetails.dayDetails.forEach(function(day) {
                obj.date = day.date;
                dailyEntries.push(obj);
            });
            if (this.weekObj.status === 'NOT SUBMITTED') {
                timesheetData.allocations.forEach(function(project) {
                    project.dailyEntries = dailyEntries;
                });
            }
            timesheetData.weekDetails.dayDetails.forEach(function(day) {
                day.comments = '';
                day.timeEntries = timeEntries;
            });
        } else if (this.weekObj.status === 'SUBMITTED') {
            timesheetData.timesheet.projectTimeSheets[0].dailyEntries.forEach(function(day) {
                day.timeEntries = timeEntries;
            });
        }
        return timesheetData;
    }
    trackByIndex(index: number, obj: any): any {
        return index;
    }
    createInitialTimesheetData() {
        if (this.weekObj.status === 'NOT SUBMITTED') {
            this.weekObj.projects = this.createProjectsModel(this.timesheetData.allocations);
            this.weekObj.daysData = this.createDaysData(this.timesheetData.weekDetails.dayDetails);
        } else if (this.weekObj.status === 'SUBMITTED') {
            this.weekObj.projects = this.createProjectsModel(this.timesheetData.timesheet.projectTimeSheets);
            this.weekObj.daysData = this.createDaysData(this.timesheetData.timesheet.projectTimeSheets[0].dailyEntries);
        } else if (this.weekObj.status === 'SAVED') {
            this.weekObj.projects = this.createProjectsModel(this.timesheetData.timesheet.projectTimeSheets);
            this.weekObj.daysData = this.createDaysData(this.timesheetData.weekDetails.dayDetails);
        }
        this.weekObj.weekName = this.timesheetData.weekDetails.weekName;
        this.weekObj.actions = this.timesheetData.actions;
        this.weekObj.startDate = this.timesheetData.weekDetails.weekStartDate;
        this.weekObj.endDate = this.timesheetData.weekDetails.weekEndDate;
        this.weekObj.weeklyStandardHours = this.timesheetData.weekDetails.weeklyStandardHours;
        this.weekObj.commentsAdded = true;
        this.weekObj.leaves = [];
        this.weekObj.previousWeekEnabled = this.timesheetData.previousWeek && this.timesheetData.previousWeek != null ? true : false;
        this.weekObj.previousWeekStartDate = this.weekObj.previousWeekEnabled && this.timesheetData.previousWeek.week.weekStartDate;
        this.weekObj.nextWeekEnabled = this.timesheetData.nextWeek && this.timesheetData.nextWeek != null ? true : false;
        this.weekObj.nextWeekStartDate = this.weekObj.nextWeekEnabled && this.timesheetData.nextWeek.week.weekStartDate;
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
                if (this.weekObj.status === 'NOT SUBMITTED' || this.weekObj.status === 'SAVED') {
                    dModel.comment = dayObj.timeEntries.comments;
                }
                daysViewModel.push(dModel);
            });
        }
        return daysViewModel;
    }

    createProjectHoursPerDayModel(i) {
        let hoursPerDayModel = [];
        let currentProj = this.weekObj.projects;
        currentProj.forEach(proj => {
            let timeObj: any = {};
            timeObj.comments = proj.hours[i].comments;
            timeObj.hours = proj.hours[i].hour;
            timeObj.projectCode = proj.code;
            hoursPerDayModel.push(timeObj);
        });
        return hoursPerDayModel;
    }

    createHourEntriesModel() {
        let dailydetails = this.timesheetData.weekDetails.dayDetails;
        let hourEntries = [];
        for (let i = 0; i < 7; i++) {
            let entryObj: any = {};
            entryObj.comments = this.weekObj.daysData[i].comment;
            entryObj.date = dailydetails[i].date;
            entryObj.timeEntries = this.createProjectHoursPerDayModel(i);
            hourEntries.push(entryObj);
        }
        return hourEntries;
    }

    saveOrSubmit(action) {
        let totalHours = this.calculateTotalHours(this.weekObj.projects);
        let dailyEntries = this.createHourEntriesModel();
        let stanardHours = this.weekObj.weeklyStandardHours;
        if (action === 'SAVE') {
            let data = {
                action: action,
                resourceCode: this.timesheetData.resource.key,
                week: {
                    weekStartDate: this.timesheetData.weekDetails.weekStartDate,
                    dailyEntries: dailyEntries
                }
            };
            this.timesheetService.saveSubmitTimesheet(data).subscribe(response => {
                let obj;
                if (response) {
                    obj = {
                        submitted: true,
                        displayLabel: 'Your timsheet has successfully saved.'
                    };
                    this.submittedToParent.emit(obj);
                }
                this.getTimeSheetDataById(parseInt(response.headers.get('x-itrack2app-params')));
            });
        }
        if (action === 'SUBMIT' && totalHours < stanardHours) {
            alert('Minimum 40 hours are reaquired per week');
        } else if (action === 'SUBMIT' && totalHours === stanardHours) {
            let data = {
                action: action,
                resourceCode: this.timesheetData.resource.key,
                week: {
                    weekStartDate: this.timesheetData.weekDetails.weekStartDate,
                    dailyEntries: dailyEntries
                }
            };
            const dialogRef = this.dialog.open(DialogConfirmBodyComponent, {
                width: '412px',
                position: { top: '130px' }
            });
            dialogRef.afterClosed().subscribe(response => {
                let obj;
                if (response) {
                    obj = {
                        submitted: true,
                        displayLabel: 'Your timsheet has successfully submitted.'
                    };
                    this.submittedToParent.emit(obj);
                    this.getTimeSheetDataById(parseInt(response.headers.get('x-itrack2app-params')));
                }
            });
        } else if (action === 'SUBMIT' && totalHours > stanardHours) {
            let data = {
                action: action,
                resourceCode: this.timesheetData.resource.key,
                week: {
                    dailyEntries: dailyEntries,
                    weekStartDate: this.timesheetData.weekDetails.weekStartDate
                }
            };
            const dialogRef = this.dialog.open(DialogConfirmBodyComponent, {
                width: '412px',
                position: { top: '130px' }
            });

            dialogRef.afterClosed().subscribe(response => {
                let obj;
                if (response) {
                    obj = {
                        submitted: true,
                        displayLabel: 'Your timsheet has successfully submitted.'
                    };
                    this.submittedToParent.emit(obj);
                    this.getTimeSheetDataById(parseInt(response.headers.get('x-itrack2app-params')));
                }
            });
        }
    }

    createProjectsModel(projects) {
        let projectsModel = [],
            condition;
        if (projects.length > 0) {
            projects.forEach(project => {
                let dataObj = {
                    code: '',
                    name: '',
                    attachment: '',
                    type: '',
                    hours: [],
                    totalHours: ''
                };
                if (this.weekObj.status === 'NOT SUBMITTED') {
                    condition = project.projectType.key !== 'PDL' && project.projectType.key !== 'UPL';
                    dataObj.code = project.project.key;
                    dataObj.name = project.project.key + ' - ' + project.project.value + ' ' + project.proportion + '%';
                    dataObj.attachment = project.customerTimeTracking;
                    dataObj.type = project.projectType.key;
                    dataObj.hours = [
                        { hour: 0, comments: '' },
                        { hour: 0, comments: '' },
                        { hour: 0, comments: '' },
                        { hour: 0, comments: '' },
                        { hour: 0, comments: '' },
                        { hour: 0, comments: '' },
                        { hour: 0, comments: '' }
                    ];
                } else if (this.weekObj.status === 'SUBMITTED' || this.weekObj.status === 'SAVED') {
                    if (this.weekObj.status === 'SUBMITTED') {
                        condition = true;
                    } else {
                        condition = true;
                        //condition = project.projectDetails.projectType.key !== 'PDL' && project.projectDetails.projectType.key !== 'UPL';
                    }
                    dataObj.code = project.projectDetails.project.key;
                    dataObj.name =
                        project.projectDetails.project.key +
                        ' - ' +
                        project.projectDetails.project.value +
                        ' ' +
                        project.projectDetails.proportion +
                        '%';
                    dataObj.attachment = project.projectDetails.customerTimeTracking;
                    dataObj.type = project.projectDetails.projectType.key;
                    project.dailyEntries.forEach(function(day) {
                        let hour = day.hours.replace(day.hours.charAt(day.hours.length - 1), '').replace('PT', '');
                        dataObj.hours.push({
                            hour: hour,
                            comments: day.comments
                        });
                    });
                    dataObj.totalHours = project.projectTotalHours;
                    //dataObj.attachment =
                }
                if (condition) {
                    const projObj = {
                        code: dataObj.code,
                        name: dataObj.name,
                        hours: dataObj.hours,
                        type: dataObj.type,
                        totalHours: dataObj.totalHours
                    };
                    projectsModel.push(projObj);
                } else {
                    const projObj = {
                        code: dataObj.code,
                        name: dataObj.name,
                        hours: dataObj.hours,
                        type: dataObj.type,
                        totalHours: dataObj.totalHours
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
        let hourss: any = 0;
        if (p && p.length > 0) {
            for (let i = 0; i < p.length; i++) {
                let h = parseInt(p[i].hours[indx].hour);
                if (isNaN(h)) {
                    hourss += 0;
                } else {
                    hourss += h;
                }
            }
        }
        hourss = hourss > 8 ? hourss + '**' : hourss;
        hourss.toString();
        return hourss;
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

    public popupTrigger(): void {
        this.popoverList.forEach(function(popover) {
            if (popover.isOpen()) {
                popover.close();
            }
        });
    }
}
