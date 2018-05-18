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
  timeTrackingAvailable: string;
}

const ELEMENT_DATA: Element[] = [
  { accountCode: 1, customerName: 'sddsf', location: 'Delhi', customerRM: 'H', accountManager: 'abc', timeTrackingAvailable: 'Yes' }
];

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', cookie: 'abc' })
};

@Component({
  selector: 'app-list-accounts',
  templateUrl: './list-accounts.component.html',
  styleUrls: ['./list-accounts.component.css']
})
export class ListAccountsComponent implements OnInit {
  displayedColumns = ['accountCode', 'customerName', 'location', 'customerRM', 'accountManager', 'timeTrackingAvailable'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);
  constructor(private router: Router, private restangular: Restangular) {}

  ngOnInit() {
    this.restangular.one('/api/accounts').get();
  }
  // public goAddAccount(){
  //   this.router.navigate(['addaccount']);
  // }
}
