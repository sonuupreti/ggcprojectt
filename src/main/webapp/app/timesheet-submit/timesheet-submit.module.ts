import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from './../material.module';
import { TimesheetViewComponent } from './timesheet-view.component';
import { PreviousTimesheetViewComponent } from './previous-timesheet-view/previous-timesheet-view.component';
import { CurrentTimesheetView } from './current-timesheet-view/current-timesheet-view.component';
import { TimesheetDetailedViewComponent } from './previous-timesheet-view/timesheet-detailed-view/timesheet-detailed-view.component';
import { timeSheetsDetailedViewDirective } from './previous-timesheet-view/timesheet-detailed-view/timesheet-detailed-view.directive';
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
    declarations: [
        TimesheetViewComponent,
        PreviousTimesheetViewComponent,
        CurrentTimesheetView,
        TimesheetDetailedViewComponent,
        timeSheetsDetailedViewDirective
    ],
    entryComponents: [TimesheetDetailedViewComponent],
    providers: [TimesheetService],
    exports: []
})
export class TimeSheetSubmitModule {}
