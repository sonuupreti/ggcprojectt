import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from 'app/app.constants';

@Injectable()
export class TimesheetService {
    constructor(private http: HttpClient) {}

    getTimesheetData(date?): Observable<any> {
        let url;
        if (date) {
            url = '/api/v1/timesheets/weekly?date=' + date;
        } else {
            url = '/api/v1/timesheets/weekly';
        }
        return this.http.get(SERVER_API_URL + url);
    }
    saveSubmitTimesheet(data): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/v1/timesheets', data, { observe: 'response' }).map((response: HttpResponse<any>) => {
            // response.timesheetId = parseInt(response.headers.get('x-itrack2app-params'));
            return response;
        });
    }
    getReturnTimesheets(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/v1/timesheets/recent', {}).map((response: HttpResponse<any>) => {
            return response;
        });
    }
    getTimeSheetDataById(id): Observable<any> {
        return this.http.get(SERVER_API_URL + '/api/v1/timesheets/weekly/' + id, {}).map((response: HttpResponse<any>) => {
            return response;
        });
    }
}
