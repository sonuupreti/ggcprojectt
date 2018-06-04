import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from './../material.module';
import { TimesheetViewComponent } from './timesheet-view.component';
import { PreviousTimesheetView } from './previous-timesheet-view/previous-timesheet-view.component';
import { CurrentTimesheetView } from './current-timesheet-view/current-timesheet-view.component';
import { TimesheetService } from './timesheet.service';

const routes = [
    {
        path: '',
        data: {
            authorities: ['ROLE_ADMIN']
        },
        component: TimesheetViewComponent
    }
];
@NgModule({
    imports: [CommonModule, FormsModule, MaterialModule, RouterModule.forChild(routes)],
    declarations: [TimesheetViewComponent, PreviousTimesheetView, CurrentTimesheetView],
    providers: [TimesheetService],
    exports: []
})
export class TimeSheetSubmitModule {}
