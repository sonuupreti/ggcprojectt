import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatSort, MatTableDataSource } from '@angular/material';
import { ProjectService } from '../projects.service';
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
    styleUrls: ['./list-project.component.css']
})
export class ListProjectsComponent implements OnInit {
    @ViewChild(MatSort) sort: MatSort;
    displayedColumns = [
        'projectCode',
        'projectName',
        'projectTypeDescription',
        'accountName',
        'location',
        'startDate',
        'endDate',
        'status'
    ];
    dataSource;
    constructor(private projectService: ProjectService) {}

    ngOnInit() {
        this.projectService.getProjectsList().subscribe(projectsList => {
            this.dataSource = new MatTableDataSource(projectsList);
            console.log(this.dataSource);
        });
    }

    search(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.dataSource.filter = filterValue;
    }
}
