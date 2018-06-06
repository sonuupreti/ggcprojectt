import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TimesheetApprovalsComponent } from 'app/timesheet-approvals/timesheet-approvals.component';

export const routes: Routes = [
    {
        path: '',
        component: TimesheetApprovalsComponent
    }
];
