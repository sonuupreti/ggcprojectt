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
    contractorFields: Array<any>;
    constructor(private fb: FormBuilder) {}
    rateMessage: string = 'Cost Rate = (Annual Salary + Overheads (Commission' +
        '+Bonus+Others))/Standard working hours per week(40)*' +
        'Number of weeks in a year(52)))';
    ngOnInit() {
        this.resourceForm = this.fb.group({
            resourceName: new FormControl('', [Validators.required]),
            companyName: new FormControl('', [Validators.required]),
            department: new FormControl('', [Validators.required]),
            designation: new FormControl('', [Validators.required]),
            joiningDate: new FormControl('', [Validators.required]),
            actualJoiningDate: new FormControl('', [Validators.required]),
            baseLocation: new FormControl('', []),
            deputedLocation: new FormControl('', []),
            employeeType: new FormControl('', []),
            empStatus: new FormControl('', [Validators.required])
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
            },
            {
                name: 'bonus',
                validators: [Validators.required]
            }
        ];

        this.contractorFields = [
            {
                name: 'currency',
                validators: [Validators.required]
            },
            {
                name: 'ratePerHour',
                validators: [Validators.required]
            },
            {
                name: 'startDate',
                validators: [Validators.required]
            },
            {
                name: 'endDate',
                validators: [Validators.required]
            },
            {
                name: 'remarks',
                validators: []
            }
        ];

        this.resourceForm.get('employeeType').valueChanges.subscribe(value => {
            let self = this;
            if (value === '1') {
                this.contractorFields.map(index => {
                    this.resourceForm.removeControl(index.name);
                });
                this.FTEFields.map(index => {
                    self.resourceForm.addControl(index.name, new FormControl('', index.validators));
                });
            } else if (value === '2' || value === '3') {
                this.FTEFields.map(index => {
                    this.resourceForm.removeControl(index.name);
                });
                this.contractorFields.map(index => {
                    self.resourceForm.addControl(index.name, new FormControl('', index.validators));
                });
            }
        });

        this.resourceForm.get('employeeType').setValue('1');
    }
}
