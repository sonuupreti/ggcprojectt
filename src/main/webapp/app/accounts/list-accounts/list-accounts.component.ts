import { Component, OnInit, ViewChild } from '@angular/core';
import { ListAccountsService } from './account.service';
import { MatSort, MatTableDataSource } from '@angular/material';

@Component({
    selector: 'jhi-list-accounts',
    templateUrl: './list-accounts.component.html',
    styleUrls: ['./list-account.component.css']
})
export class ListAccountsComponent implements OnInit {
    @ViewChild(MatSort) sort: MatSort;
    displayedColumns = [
        'accountCode',
        'customerName',
        'location',
        'customerReportingManager',
        'accountManagerName',
        'customerTimeTracking'
    ];
    dataSource;
    constructor(private listAccountService: ListAccountsService) {}

    ngOnInit() {
        this.listAccountService.getAccountList().subscribe(listAccounts => {
            this.dataSource = new MatTableDataSource(listAccounts);
            this.dataSource.sort = this.sort;
        });

        //this.dataSource.sort = this.sort;
    }

    search(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.dataSource.filter = filterValue;
    }
}
