import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MaterialModule } from './../material.module';
import { TimesheetViewComponent } from './timesheet-view.component';
import { PreviousTimesheetViewComponent } from './previous-timesheet-view/previous-timesheet-view.component';
import { CurrentTimesheetView } from './current-timesheet-view/current-timesheet-view.component';
import { TimesheetDetailedViewComponent } from './previous-timesheet-view/timesheet-detailed-view/timesheet-detailed-view.component';
import { TimeSheetsDetailedViewDirective } from './previous-timesheet-view/timesheet-detailed-view/timesheet-detailed-view.directive';
import { TimesheetService } from './timesheet.service';
import { EllipsisPipe } from './previous-timesheet-view/ellipsis.pipe';
import { DateSuffixPipe } from './datesuffix.pipe';

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
    imports: [CommonModule, FormsModule, NgbModule.forRoot(), MaterialModule, RouterModule.forChild(routes)],
    declarations: [
        TimesheetViewComponent,
        PreviousTimesheetViewComponent,
        CurrentTimesheetView,
        TimesheetDetailedViewComponent,
        TimeSheetsDetailedViewDirective,
        EllipsisPipe,
        DateSuffixPipe
    ],
    entryComponents: [TimesheetDetailedViewComponent],
    providers: [TimesheetService],
    exports: []
})
export class TimeSheetSubmitModule {}
