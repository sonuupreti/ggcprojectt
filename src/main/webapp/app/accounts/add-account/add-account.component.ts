import { Component, OnInit } from '@angular/core';
import { AddAccountService } from './add-account.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-add-account',
    templateUrl: './add-account.component.html',
    styleUrls: ['./add-account.component.css']
})
export class AddAccountComponent implements OnInit {
    locationList: Array<any> = [];
    accountCode: string;
    accountManagerList: Array<any> = [];
    isTimeTrackingAvailable: number = 1;
    selectedAccountManager: any = {};
    selectedLocation: any = {};
    customerEntity: string;
    customerName: string;
    customerReportingManager: string;
    constructor(private addAccountService: AddAccountService, private router: Router) {}

    ngOnInit() {
        this.addAccountService.getLocationAndAccountManagerList().subscribe(data => {
            this.locationList = data.locationsList;
            this.accountManagerList = data.accountManagerList;
        });
    }
    public saveAccountDetails() {
        let data = {
            accountManagerCode: this.selectedAccountManager,
            cityId: this.selectedLocation,
            customerEntity: this.customerEntity,
            customerName: this.customerName,
            customerReportingManager: this.customerReportingManager,
            customerTimeTracking: this.isTimeTrackingAvailable === 0 ? false : true
        };
        this.addAccountService.saveAccountDetails(data).subscribe(data => {
            localStorage.setItem('previousUrl', 'createaccount');
            this.router.navigate(['view-account', data.accountCode]);
        });
    }
}
