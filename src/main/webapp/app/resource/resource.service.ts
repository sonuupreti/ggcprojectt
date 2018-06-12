import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable()
export class ResourceService {
    constructor(private http: HttpClient) {}

    initAddResource(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/initAddResource');
    }

    saveResourceDetails(data): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/resource', data, {}).map((response: HttpResponse<any>) => {
            return response;
        });
    }
    getResourceDetails(resourceCode): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/resource/' + resourceCode);
    }
}
