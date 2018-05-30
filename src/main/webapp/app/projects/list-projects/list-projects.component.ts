import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material';
import { ListProjectsService } from './list-projects.service';
export interface Element {
    accountCode: number;
    customerName: string;
    location: string;
    customerRM: string;
    accountManager: string;
    startDate: string;
    endDate: string;
    status: string;
}

@Component({
    selector: 'jhi-list-projects',
    templateUrl: './list-projects.component.html',
    styles: []
})
export class ListProjectsComponent implements OnInit {
    displayedColumns = [
        'accountCode',
        'customerName',
        'location',
        'customerReportingManager',
        'accountManagerName',
        'customerTimeTracking'
    ];
    dataSource;
    constructor(private listProjectsService: ListProjectsService) {}

    ngOnInit() {
        this.listProjectsService.getProjectsList().subscribe(projectsList => {
            this.dataSource = new MatTableDataSource(projectsList);
            console.log(this.dataSource);
        });
    }
}
