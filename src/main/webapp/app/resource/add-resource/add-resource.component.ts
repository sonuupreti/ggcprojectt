import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, FormBuilder, FormGroupDirective, FormGroup, NgForm, Validators } from '@angular/forms';

@Component({
    selector: 'jhi-add-resource',
    templateUrl: './add-resource.component.html',
    styleUrls: ['./add-resource.component.css']
})
export class AddResourceComponent implements OnInit {
    resourceForm: FormGroup;
    FTEFields: Array<any>;
    constructor(private fb: FormBuilder) {}

    ngOnInit() {
        this.resourceForm = this.fb.group({
            resourceName: new FormControl('', []),
            companyName: new FormControl('', []),
            department: new FormControl('', []),
            joiningDate: new FormControl('', [Validators.required]),
            actualJoiningDate: new FormControl('', [Validators.required]),
            baseLocation: new FormControl('', []),
            deputedLocation: new FormControl('', []),
            employeeType: new FormControl('', [])
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

        this.FTEFields = [
            {
                name: 'anualPackage',
                validators: [Validators.required]
            },
            {
                name: 'currency',
                validators: [Validators.required]
            },
            {
                name: 'commision',
                validators: [Validators.required]
            }
        ];

        this.resourceForm.get('employeeType').valueChanges.subscribe(value => {
            if (value === '1') {
                let self = this;
                this.FTEFields.map(index => {
                    self.resourceForm.addControl(index.name, new FormControl('', index.validators));
                });
            } else if (value === '2') {
                this.FTEFields.map(index => {
                    this.resourceForm.removeControl(index.name);
                });
            }
        });

        this.resourceForm.get('employeeType').setValue('1');
    }
}
