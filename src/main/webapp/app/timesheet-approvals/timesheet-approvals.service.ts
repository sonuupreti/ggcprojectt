import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class TimesheetApprovalsService {
    constructor(private http: HttpClient) {}

    // getPendingTimesheetData(): Observable<any> {
    //     //return this.http.get('src/main/webapp/app/mockResponse/timeSeetResponse.json');
    // }
}
