import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormsModule, FormBuilder, FormGroupDirective, FormGroup, NgForm, Validators } from '@angular/forms';
import { NgbPopoverConfig } from '@ng-bootstrap/ng-bootstrap';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatAutocompleteSelectedEvent, MatChipInputEvent } from '@angular/material';
import { Observable } from 'rxjs';
import { DatePipe } from '@angular/common';

import { Router } from '@angular/router';

import { ResourceService } from '../resource.service';
export const DATE_FORMATE = 'yyyy-MM-dd';

@Component({
    selector: 'jhi-add-resource',
    templateUrl: './add-resource.component.html',
    styleUrls: ['./add-resource.component.css']
})
export class AddResourceComponent implements OnInit {
    resourceForm: FormGroup;
    primarySkill: Array<any> = [];

    FTEFields: Array<any>;
    contractorFields: Array<any>;
    companiesList: Array<any>;
    locationsList: Array<any>;
    departmentList: Array<any>;
    designationList: Array<any>;
    currencyList: Array<any>;
    technologyList: Array<any>;

    selectable: boolean = true;
    removable: boolean = true;
    addOnBlur: boolean = false;

    separatorKeysCodes = [ENTER, COMMA];

    primarySkillCtrl = new FormControl();

    filteredTechnologies: Array<any>;

    @ViewChild('technologyInput') technologyInput: ElementRef;

    constructor(
        private fb: FormBuilder,
        private resourceService: ResourceService,
        private config: NgbPopoverConfig,
        private router: Router
    ) {
        config.placement = 'top';
        config.triggers = 'hover';
        this.primarySkillCtrl.valueChanges.subscribe(x => {
            console.log(this.primarySkillCtrl);
            const self = this;
            this.filteredTechnologies = this.technologyList.filter(function(index) {
                if (self.primarySkillCtrl.value) {
                    if (typeof self.primarySkillCtrl.value === 'object') {
                        return index.value.toLocaleLowerCase().indexOf(self.primarySkillCtrl.value.value.toLocaleLowerCase()) >= 0;
                    } else {
                        return index.value.toLocaleLowerCase().indexOf(self.primarySkillCtrl.value.toLocaleLowerCase()) >= 0;
                    }
                }
            });
        });
    }
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
            baseLocation: new FormControl('', [Validators.required]),
            employeeType: new FormControl('', []),
            technology: new FormControl('', [Validators.required])
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

        this.resourceService.initAddResource().subscribe(response => {
            this.companiesList = response.companiesList;
            this.locationsList = response.locationsList;
            this.departmentList = response.departmentList;
            this.designationList = response.designationList;
            this.currencyList = response.currencyPairs;
            this.technologyList = response.technologiesPairs;
        });
    }
    saveData(event) {
        event.preventDefault();
        let datePipe = new DatePipe('en-US');
        // if(){

        // }
        const data = {
            name: this.resourceForm.get('resourceName').value,
            annualSalary: this.resourceForm.get('anualPackage').value,
            baseLocationId: this.resourceForm.get('baseLocation').value.key,
            companyId: this.resourceForm.get('companyName').value.key,
            departmentId: this.resourceForm.get('department').value.key,
            designationId: this.resourceForm.get('designation').value.key,
            primarySkills: JSON.stringify(this.primarySkill),
            employmentTypeCode: 'FTE', //this.resourceForm.get('employeeType').value,
            gender: 'MALE',
            expectedJoiningDate: datePipe.transform(this.resourceForm.get('joiningDate').value, DATE_FORMATE)
        };

        this.resourceService.saveResourceDetails(data).subscribe(response => {
            localStorage.setItem('previousUrl', 'addProject');
            this.router.navigate(['resource/view-resource/' + response.resourceCode]);
        });
    }

    navigateTo() {
        this.router.navigate(['./resource/view-resource']);
    }

    add(event: MatChipInputEvent): void {
        const input = event.input;
        const value = event.value;
        if ((value || '').trim()) {
            let flag = this.primarySkill.filter(index => {
                return index.value.toLocaleLowerCase() === event.value.toLocaleLowerCase();
            });
            if (flag.length === 0) {
                this.primarySkill.push({ key: 0, value: value.trim() });
            }
        }
        // Reset the input value
        if (input) {
            input.value = '';
        }
        this.resourceForm.get('technology').setValue('field');
    }

    remove(list: any): void {
        const index = this.primarySkill.indexOf(list);
        if (index >= 0) {
            this.primarySkill.splice(index, 1);
        }
        this.technologyList.push(list);
        if (this.primarySkill.length === 0) {
            this.resourceForm.get('technology').setValue('');
        }
    }

    selected(event: MatAutocompleteSelectedEvent): void {
        this.resourceForm.get('technology').setValue('field');
        this.primarySkill.push({ key: event.option.value.key, value: event.option.value.value });
        const self = this;
        let index = self.technologyList.indexOf(event.option.value);
        this.technologyList.splice(index, 1);
        this.technologyInput.nativeElement.value = '';
        this.primarySkillCtrl.setValue(null);
    }
    getSelected(list, code): any {
        return list.find(index => {
            return index.key === code;
        });
    }

    reset() {
        this.resourceForm.reset();
        this.resourceForm.get('technology').setValue('');
        this.primarySkill = [];
    }
}
