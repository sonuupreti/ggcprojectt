import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
    selector: 'jhi-remove-account-confirm-dialog',
    templateUrl: './remove-account-confirm-dialog.component.html',
    styles: []
})
export class RemoveAccountConfirmDialogComponent implements OnInit {
    constructor(public dialogRef: MatDialogRef<RemoveAccountConfirmDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

    onNoClick(): void {
        this.dialogRef.close();
    }

    ngOnInit() {}
}
