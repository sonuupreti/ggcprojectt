import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable()
export class ProjectService {
    constructor(private http: HttpClient) {}

    initAddProject(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/initAddProject');
    }
    getProjectsList(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/projects');
    }

    saveProjectDetails(data): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/projects', data, {}).map((response: HttpResponse<any>) => {
            return response;
        });
    }
    updateProjectDetails(data): Observable<any> {
        return this.http.put(SERVER_API_URL + 'api/projects', data, {}).map((response: HttpResponse<any>) => {
            return response;
        });
    }

    getProjectDetails(projectId): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/projects/' + projectId);
    }
}
