import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-pending-requests',
    templateUrl: './pending-requests.component.html',
    styleUrls: ['./pending-requests.component.css']
})
export class PendingRequestsComponent implements OnInit {
    selected = 'RecentFirst';
    constructor() {}

    ngOnInit() {}
}
