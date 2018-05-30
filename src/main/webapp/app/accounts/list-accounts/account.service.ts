import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable()
export class ListAccountsService {
    constructor(private http: HttpClient) {}

    getMetrics(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'management/metrics');
    }

    getAccountList(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/accounts');
    }

    threadDump(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'management/threaddump');
    }
}
