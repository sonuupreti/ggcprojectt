import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormsModule, FormBuilder, FormGroupDirective, FormGroup, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatAutocompleteSelectedEvent, MatChipInputEvent } from '@angular/material';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { map, startWith } from 'rxjs/operators';

import { ProjectService } from '../projects.service';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import * as moment from 'moment';

import { Router } from '@angular/router';

export class MyErrorStateMatcher implements ErrorStateMatcher {
    isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
        const isSubmitted = form && form.submitted;
        return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
    }
}
export const DATE_FORMATE = 'yyyy-MM-dd';

@Component({
    selector: 'jhi-add-project',
    templateUrl: './add-project.component.html',
    styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent implements OnInit {
    projectForm: FormGroup;
    // accountCode: any;
    // customerProjectId: string;
    // projectName: string;
    // projectType: any;
    // status: any;
    // location: any;
    // startDate: string;
    // endDate: Date;
    // offshoreManager: any;
    // onshoreManager: any;
    // customerManager: string;

    practices: Array<any>;
    technologies: Array<any> = [];

    accountList: Array<any>;
    locationsList: Array<any>;
    practiceList: Array<any>;
    projectTypeList: Array<any>;
    resourceManagerList: Array<any>;
    statusList: Array<any>;
    technologyList: Array<any>;

    visible: boolean = true;
    selectable: boolean = true;
    removable: boolean = true;
    addOnBlur: boolean = false;

    separatorKeysCodes = [ENTER, COMMA];

    technologyCtrl = new FormControl();

    filteredTechnologies: Array<any>;

    @ViewChild('technologyInput') technologyInput: ElementRef;

    constructor(private projectService: ProjectService, private router: Router, private route: ActivatedRoute, private fb: FormBuilder) {
        this.technologyCtrl.valueChanges.subscribe(x => {
            console.log(this.technologyCtrl);
            var self = this;
            this.filteredTechnologies = this.technologyList.filter(function(index) {
                if (self.technologyCtrl.value) {
                    if (typeof self.technologyCtrl.value === 'object') {
                        return index.value.toLocaleLowerCase().indexOf(self.technologyCtrl.value.value.toLocaleLowerCase()) >= 0;
                    } else {
                        return index.value.toLocaleLowerCase().indexOf(self.technologyCtrl.value.toLocaleLowerCase()) >= 0;
                    }
                }
            });
        });
    }

    ngOnInit() {
        this.projectService.initAddProject().subscribe(response => {
            this.accountList = response.accountList;
            this.projectTypeList = response.projectTypeList;
            this.locationsList = response.locationsList;
            this.resourceManagerList = response.resourceManagerList;
            this.practiceList = response.practiceList;
            this.statusList = response.projectStatusList;
            this.technologyList = response.technologyList;
            if (this.route.snapshot.params.accountCode) {
                this.projectForm.get('accountCode').setValue(this.getSelected(this.accountList, this.route.snapshot.params.accountCode));
            }
        });
        this.projectForm = this.fb.group({
            accountCode: new FormControl('', [Validators.required]),
            customerProjectId: new FormControl('', []),
            customerManager: new FormControl('', [Validators.required]),
            projectName: new FormControl('', [Validators.required]),
            projectTypeCode: new FormControl('', [Validators.required]),
            cityId: new FormControl('', [Validators.required]),
            startDate: new FormControl('', [Validators.required]),
            endDate: new FormControl('', [Validators.required]),
            offshoreManagerCode: new FormControl('', [Validators.required]),
            onshoreManagerCode: new FormControl('', [Validators.required]),
            practices: new FormControl('', [Validators.required]),
            projectStatusCode: new FormControl('', [Validators.required]),
            technology: new FormControl('', [Validators.required])
        });
    }

    matcher = new MyErrorStateMatcher();

    save(event) {
        event.preventDefault();
        let datePipe = new DatePipe('en-US');
        const practiceList = this.practices;
        console.log();
        const data = {
            accountCode: this.projectForm.get('accountCode').value.key,
            customerProjectId: this.projectForm.get('customerProjectId').value,
            projectName: this.projectForm.get('projectName').value,
            projectTypeCode: this.projectForm.get('projectTypeCode').value.key,
            customerManager: this.projectForm.get('customerManager').value,
            cityId: this.projectForm.get('cityId').value.key,
            startDate: datePipe.transform(this.projectForm.get('startDate').value, DATE_FORMATE),
            endDate: datePipe.transform(this.projectForm.get('endDate').value, DATE_FORMATE),
            offshoreManagerCode: this.projectForm.get('offshoreManagerCode').value.key,
            onshoreManagerCode: this.projectForm.get('onshoreManagerCode').value.key,
            practiceList: this.projectForm.get('practices').value,
            technologyList: this.technologies,
            projectStatusCode: this.projectForm.get('projectStatusCode').value.key
        };
        this.projectService.saveProjectDetails(data).subscribe(response => {
            localStorage.setItem('previousUrl', 'addProject');
            this.router.navigate(['projects/view-project/' + response.projectCode]);
        });
    }

    add(event: MatChipInputEvent): void {
        const input = event.input;
        const value = event.value;
        if ((value || '').trim()) {
            let flag = this.technologies.filter(index => {
                return index.value.toLocaleLowerCase() === event.value.toLocaleLowerCase();
            });
            if (flag.length === 0) {
                this.technologies.push({ key: 0, value: value.trim() });
            }
        }
        // Reset the input value
        if (input) {
            input.value = '';
        }
        this.projectForm.get('technology').setValue('field');
    }

    remove(list: any): void {
        const index = this.technologies.indexOf(list);
        if (index >= 0) {
            this.technologies.splice(index, 1);
        }
        this.technologyList.push(list);
        if (this.technologies.length === 0) {
            this.projectForm.get('technology').setValue('');
        }
    }

    selected(event: MatAutocompleteSelectedEvent): void {
        this.projectForm.get('technology').setValue('field');
        this.technologies.push({ key: event.option.value.key, value: event.option.value.value });
        let self = this;
        let index = self.technologyList.indexOf(event.option.value);
        this.technologyList.splice(index, 1);
        this.technologyInput.nativeElement.value = '';
        this.technologyCtrl.setValue(null);
    }
    getSelected(list, code): any {
        return list.find(index => {
            return index.key === code;
        });
    }

    reset() {
        this.projectForm.reset();
        this.projectForm.get('technology').setValue('');
        this.technologies = [];
    }
}
