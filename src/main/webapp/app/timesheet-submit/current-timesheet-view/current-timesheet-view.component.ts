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
    timesheetData: any = {};

    constructor(private timesheetService: TimesheetService) {}

    ngOnInit() {
        this.currentDate = new Date();
        //this.createWeekData(this.currentDate);
        this.timesheetService.getGetInitialTimesheet().subscribe(timesheetData => {
            this.timesheetData = timesheetData;
            this.createInitialTimesheetData();
        });
    }
    trackByIndex(index: number, obj: any): any {
        return index;
    }
    createInitialTimesheetData() {
        this.weekObj.projects = this.createProjectsModel(this.timesheetData.resourceAllocationSummary.projects);
        this.weekObj.daysData = this.createDaysData(this.timesheetData.weekDetails.dailyDetails);
        this.weekObj.status = 'NOT SUBMITTED';
        this.weekObj.actions = this.timesheetData.actions;
    }

    createDaysData(daysData) {
        let daysViewModel = [];
        if (daysData.length > 0) {
            daysData.forEach(dayObj => {
                let dModel: any = {};
                let splittedDateObj = dayObj.date.split('-');
                dModel.date = splittedDateObj[2];
                dModel.month = this.months[parseInt(splittedDateObj[1]) - 1];
                dModel.day = dayObj.day;
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
            timeObj.comments = comment;
            timeObj.hours = parseInt(proj.hours[i]);
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
        if (action === 'SUBMIT' && totalHours < 40) {
            alert('Minimum 40 hours are reaquired per week');
        } else {
            let data = {
                action: action,
                resourceCode: '20001',
                week: {
                    dailyEntries: this.createHourEntriesModel(),
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
        }
    }

    createWeekData(d) {
        let dd = d.getDate();
        let wd = d.getDay();
        let dy = d.getFullYear();
        this.daysInMonths[1] = this.getFebDaysForYear(dy);

        this.weekObj.weekNo = this.getWeek(d, 1);
        this.weekObj.year = d.getFullYear();
        this.weekObj.startDate = dd - (wd - 1);
        this.weekObj.startMonth = d.getMonth();
        this.weekObj.projects = this.createProjectsModel(this.projects);
        this.weekObj.status = 'NOT SUBMITTED';
        //this.weekObj.currentWeek = true;
    }

    createProjectsModel(projects) {
        let projectsModel = [];
        if (projects.length > 0) {
            projects.forEach(function(project) {
                if (project.projectType.key != 'PDL' && project.projectType.key != 'UPL') {
                    let projObj = {
                        code: project.project.key,
                        name: project.project.key + ' - ' + project.project.value + ' ' + project.proportion + '%',
                        hours: [0, 0, 0, 0, 0, 0, 0],
                        attachment: project.customerTimeTracking
                    };
                    projectsModel.push(projObj);
                }
            });
        }
        return projectsModel;
    }

    getFebDaysForYear(yr) {
        if (yr % 4 === 0) {
            return 29;
        }
        return 28;
    }

    getChangedMonthDate(d, m) {
        if (d > this.daysInMonths[m]) {
            return d - this.daysInMonths[m];
        }
        return d;
    }

    getChangedMonth(sDate, lDate, m) {
        if (lDate < sDate) {
            return m + 1;
        }
        return m;
    }

    calculateProjectHours(a) {
        let hours = 0;
        if (a && a.length > 0) {
            for (let i = 0; i < a.length; i++) {
                let h = parseInt(a[i]);
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
                let h = parseInt(p[i].hours[indx]);
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
                        let h = parseInt(p[i].hours[j]);
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
}
