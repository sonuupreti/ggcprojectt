import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
@Injectable()
export class AddAccountService {
    constructor(private http: HttpClient) {}

    getLocationAndAccountManagerList(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/initAddAccount');
    }

    saveAccountDetails(data): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/accounts', data, {}).map((response: HttpResponse<any>) => {
            return response;
        });
    }
}
