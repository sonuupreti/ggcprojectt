import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material';
import { Http } from '@angular/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { Restangular } from 'ngx-restangular';

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

const ELEMENT_DATA: Element[] = [
  {
    accountCode: 1,
    customerName: 'Hydrogen',
    location: 'Delhi',
    customerRM: 'H',
    accountManager: 'abc',
    startDate: '04/12/2018',
    endDate: '04/12/2018',
    status: 'completed'
  }
];

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', cookie: 'abc' })
};

@Component({
  selector: 'app-list-projects',
  templateUrl: './list-projects.component.html',
  styleUrls: ['./list-projects.component.css']
})
export class ListProjectsComponent implements OnInit {
  displayedColumns = ['accountCode', 'customerName', 'location', 'customerRM', 'accountManager', 'startDate', 'endDate', 'status'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);
  constructor() {}

  ngOnInit() {}
}
