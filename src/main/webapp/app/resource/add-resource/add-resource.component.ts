import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, FormBuilder, FormGroupDirective, FormGroup, NgForm, Validators } from '@angular/forms';

@Component({
    selector: 'jhi-add-resource',
    templateUrl: './add-resource.component.html',
    styleUrls: ['./add-resource.component.css']
})
export class AddResourceComponent implements OnInit {
    resourceForm: FormGroup;
    constructor(private fb: FormBuilder) {}

    ngOnInit() {
        this.resourceForm = this.fb.group({
            resourceName: new FormControl('', [Validators.required]),
            companyName: new FormControl('', [Validators.required]),
            department: new FormControl('', [Validators.required]),
            joiningDate: new FormControl('', [Validators.required]),
            actualJoiningDate: new FormControl('', [Validators.required]),
            baseLocation: new FormControl('', []),
            deputedLocation: new FormControl('', [])
            // customerManager: new FormControl('', [Validators.required]),
            // projectName: new FormControl('', [Validators.required]),
            // projectTypeCode: new FormControl('', [Validators.required]),
            // cityId: new FormControl('', [Validators.required]),
            // startDate: new FormControl('', [Validators.required]),
            // endDate: new FormControl('', [Validators.required]),
            // offshoreManagerCode: new FormControl('', [Validators.required]),
            // onshoreManagerCode: new FormControl('', [Validators.required]),
            // practices: new FormControl('', [Validators.required]),
            // projectStatusCode: new FormControl('', [Validators.required]),
            // technology: new FormControl('', [Validators.required])
        });
    }
}
