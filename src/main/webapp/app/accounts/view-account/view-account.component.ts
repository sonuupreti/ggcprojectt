import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ViewAccountService } from './view-account.service';
import { Router } from '@angular/router';
import 'rxjs/add/operator/filter';
import { MatDialog } from '@angular/material';
import { RemoveAccountConfirmDialogComponent } from '../../../app/dialog/remove-account-confirm-dialog/remove-account-confirm-dialog.component';

@Component({
    selector: 'jhi-view-account',
    templateUrl: './view-account.component.html',
    styleUrls: ['./view-account.component.css'],
    entryComponents: [RemoveAccountConfirmDialogComponent]
})
export class ViewAccountComponent implements OnInit {
    isViewMode = true;
    locationList: Array<any> = [];
    accountCode: string;
    accountManagerList: Array<any> = [];
    isTimeTrackingAvailable: number = 1;
    selectedAccountManager: any;
    selectedLocation: any;
    customerEntity: string;
    customerName: string;
    customerReportingManager: string;
    showMessage = false;
    currentUrl: string;
    actionMessage: string;
    previousUrl: string;
    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private viewAccountService: ViewAccountService,
        public dialog: MatDialog
    ) {
        let previousUrl = localStorage.getItem('previousUrl');
        if (previousUrl === 'createaccount') {
            this.showMessage = true;
            localStorage.setItem('previousUrl', 'null');
            this.actionMessage = 'Your Account Has Successfully Been Added';
        }
    }

    ngOnInit() {
        this.viewAccountService.getLocationAndAccountManagerList(this.route.snapshot.params.accountCode).subscribe(data => {
            this.locationList = data.locationsList;
            this.accountManagerList = data.accountManagerList;
            this.viewAccountService.viewAccountDetails(this.route.snapshot.params.accountCode).subscribe(response => {
                this.accountCode = response.accountCode;
                this.customerName = response.customerName;
                this.customerEntity = response.customerEntity;
                this.customerReportingManager = response.customerReportingManager;
                this.isTimeTrackingAvailable = response.customerTimeTracking === false ? 0 : 1;
                this.selectedLocation = response.cityId;
                this.selectedAccountManager = response.accountManagerCode;
            });
        });
    }

    public updateAccountDetails() {
        let data = {
            accountCode: this.accountCode,
            accountManagerCode: this.selectedAccountManager,
            cityId: this.selectedLocation,
            customerEntity: this.customerEntity,
            customerName: this.customerName,
            customerReportingManager: this.customerReportingManager,
            customerTimeTracking: this.isTimeTrackingAvailable === 0 ? false : true
        };
        this.viewAccountService.updateAccountDetails(data).subscribe(data => {
            this.isViewMode = true;
            this.showMessage = true;
            this.actionMessage = 'Changes Saved';
        });
    }
    enableEditMode(): void {
        this.showMessage = false;
        this.isViewMode = false;
    }
    remove(): void {
        const dialogRef = this.dialog.open(RemoveAccountConfirmDialogComponent, {
            width: '412px',
            position: { top: '130px' }
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.viewAccountService.deleteAccount(this.accountCode).subscribe(data => {
                    this.router.navigate(['listaccounts']);
                });
            }
        });
    }

    navigateToProjectPage() {
        this.router.navigate(['projects/add-project', { accountCode: this.accountCode }]);
    }
}
