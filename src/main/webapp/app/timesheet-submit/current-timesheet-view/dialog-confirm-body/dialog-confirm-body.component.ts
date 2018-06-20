import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-dialog-confirm-body',
    templateUrl: './dialog-confirm-body.component.html',
    styles: [
        `.confirm-submit{
    background-color: #0EB3F7;
    color: white;
}

.cancel-submit{
background-color: lightgray;
}`
    ]
})
export class DialogConfirmBodyComponent implements OnInit {
    constructor() {}

    ngOnInit() {}
}
