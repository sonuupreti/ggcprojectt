import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
@Injectable()
export class ViewAccountService {
    constructor(private http: HttpClient) {}

    viewAccountDetails(accountCode): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/accounts/' + accountCode);
    }
    getLocationAndAccountManagerList(accountCode): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/initEditAccount/' + accountCode);
    }
    updateAccountDetails(data): Observable<any> {
        return this.http.put(SERVER_API_URL + 'api/accounts', data, {}).map((response: HttpResponse<any>) => {
            return response;
        });
    }
    deleteAccount(accountCode): Observable<any> {
        return this.http.delete(SERVER_API_URL + 'api/accounts/' + accountCode);
    }
}
