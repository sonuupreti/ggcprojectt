import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injectable } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';
import { MaterialModule } from './material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { ListAccountsComponent } from './modules/accounts/list-accounts/list-accounts.component';
import { AddAccountComponent } from './modules/accounts/add-account/add-account.component';
import { TimesheetView } from './modules/timesheet-view/timesheet-view.component';
import { PreviousTimesheetView } from './modules/timesheet-view/previous-timesheet-view/previous-timesheet-view.component';
import { CurrentTimesheetView } from './modules/timesheet-view/current-timesheet-view/current-timesheet-view.component';
import { RestangularModule } from 'ngx-restangular';
import {
  HttpClient,
  HttpInterceptor,
  HttpXsrfTokenExtractor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HTTP_INTERCEPTORS,
  HttpClientModule,
  HttpClientXsrfModule
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { ListProjectsComponent } from './modules/projects/list-projects/list-projects.component';
import { AddProjectsComponent } from './modules/projects/add-projects/add-projects.component';

const appRoutes: Routes = [
  { path: 'listaccounts', component: ListAccountsComponent },
  { path: 'addaccount', component: AddAccountComponent },
  { path: 'listprojects', component: ListProjectsComponent },
  { path: 'addproject', component: AddProjectsComponent },
  {
    path: 'timesheet',
    component: TimesheetView,
    data: {
      authorities: ['ROLE_ADMIN']
    }
  }
];

export function RestangularConfigFactory(RestangularProvider) {
  RestangularProvider.setBaseUrl('http://localhost:8080');
  RestangularProvider.setDefaultHeaders({
    Authorization: 'Bearer UDXPx-Xko0w4BRKajozCVy20X11MRZs1',
    Cookie: 'JSESSIONID=b_rIJm61qTNvr82XzQ2mlvrKbkaBZ1wkF2WCVD1S'
  });
}

@NgModule({
  declarations: [
    AppComponent,
    ListAccountsComponent,
    AddAccountComponent,
    TimesheetView,
    PreviousTimesheetView,
    CurrentTimesheetView,
    ListProjectsComponent,
    AddProjectsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    BrowserAnimationsModule,
    MaterialModule,
    RouterModule.forRoot(appRoutes, { enableTracing: false }),
    FlexLayoutModule,
    RestangularModule.forRoot(RestangularConfigFactory)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
