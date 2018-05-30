import { Component, OnInit } from '@angular/core';
//import { MatInputModule, MatButtonModule } from '@angular/material';
import { PROJECTS } from '../projects.service';

@Component({
  selector: 'current-timesheet-view',
  templateUrl: './current-timesheet-view.component.html',
  styleUrls: ['./current-timesheet-view.css']
})
export class CurrentTimesheetView implements OnInit {
  projects = PROJECTS;
  currentDate;
  private weekObj = { weekNo: 0, year: 0, startDate: 0, startMonth: '', projects: [], status: '' }; // WeekObj ;
  private months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  private daysOfWeek = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
  private daysInMonths = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
  constructor() {}

  ngOnInit() {
    this.currentDate = new Date();
    this.createWeekData(this.currentDate);
  }
  trackByIndex(index: number, obj: any): any {
    return index;
  }

  createWeekData(d) {
    let dd = d.getDate();
    let wd = d.getDay();
    let dy = d.getFullYear();
    this.daysInMonths[1] = this.getFebDaysForYear(dy);

    this.weekObj.weekNo = this.getWeek(d,1);
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
        let projObj = { name: project, hours: [0, 0, 0, 0, 0, 0, 0], attachment: true };
        projectsModel.push(projObj);
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
    if (a.length > 0) {
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
    if (p.length > 0) {
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
    if (p.length > 0) {
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
  getWeek (d,dowOffset) {
	    dowOffset = typeof(dowOffset) == 'number' ? dowOffset : 0; //default dowOffset to zero
	    let newYear = new Date(d.getFullYear(),0,1);
	    let day = newYear.getDay() - dowOffset; //the day of week the year begins on
	    day = (day >= 0 ? day : day + 7);
	    let daynum = Math.floor((d.getTime() - newYear.getTime() - 
	    (d.getTimezoneOffset()-newYear.getTimezoneOffset())*60000)/86400000) + 1;
	    let weeknum;
	    //if the year starts before the middle of a week
	    if(day < 4) {
	        weeknum = Math.floor((daynum+day-1)/7) + 1;
	        if(weeknum > 52) {
	            let nYear = new Date(d.getFullYear() + 1,0,1);
	            let nday = nYear.getDay() - dowOffset;
	            nday = nday >= 0 ? nday : nday + 7;
	            /*if the next year starts before the middle of
	              the week, it is week #1 of that year*/
	            weeknum = nday < 4 ? 1 : 53;
	        }
	    }
	    else {
	        weeknum = Math.floor((daynum+day-1)/7);
	    }
	    return weeknum;
	}
}
