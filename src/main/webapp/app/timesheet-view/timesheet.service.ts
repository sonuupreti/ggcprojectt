import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable()
export class TimesheetService {
    constructor(private http: HttpClient) {}

    getGetInitialTimesheet(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/initSubmission/20001');
    }
    saveSubmitTimesheet(data): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/timesheets', data, {}).map((response: HttpResponse<any>) => {
            return response;
        });
    }
}
