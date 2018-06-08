import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ErrorStateMatcher } from '@angular/material/core';
import { FormControl, FormsModule, FormBuilder, FormGroupDirective, FormGroup, NgForm, Validators } from '@angular/forms';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatAutocompleteSelectedEvent, MatChipInputEvent } from '@angular/material';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { DatePipe } from '@angular/common';

import { ProjectService } from '../projects.service';
import { ActivatedRoute } from '@angular/router';

export const DATE_FORMATE = 'yyyy-MM-dd';

@Component({
    selector: 'jhi-view-project',
    templateUrl: './view-project.component.html',
    styleUrls: ['./view-project.component.css']
})
export class ViewProjectComponent implements OnInit {
    isViewMode = true;
    projectForm: FormGroup;
    showMessage: boolean;
    actionMessage: string;

    practices: Array<any> = [];
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

    constructor(private projectService: ProjectService, private activatedRoute: ActivatedRoute, private fb: FormBuilder) {
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

        let previousUrl = localStorage.getItem('previousUrl');
        if (previousUrl === 'addProject') {
            this.showMessage = true;
            localStorage.setItem('previousUrl', 'null');
            this.actionMessage = 'Your Project Has Been Successfully Been Added';
        }
    }

    ngOnInit() {
        this.projectForm = this.fb.group({
            projectCode: new FormControl('', []),
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
        const projectCode = this.activatedRoute.snapshot.params.projectCode;
        this.projectService.initAddProject().subscribe(response => {
            this.accountList = response.accountList;
            this.projectTypeList = response.projectTypeList;
            this.locationsList = response.locationsList;
            this.resourceManagerList = response.resourceManagerList;
            this.practiceList = response.practiceList;
            this.statusList = response.projectStatusList;
            this.technologyList = response.technologyList;

            this.projectService.getProjectDetails(projectCode).subscribe(response => {
                this.projectForm.get('projectCode').setValue(response.projectCode);
                this.projectForm.get('accountCode').setValue(this.getSelected(this.accountList, response.accountCode));
                this.projectForm.get('customerProjectId').setValue(response.customerProjectId);
                this.projectForm.get('customerManager').setValue(response.customerManager);
                this.projectForm.get('projectName').setValue(response.projectName);
                this.projectForm.get('projectTypeCode').setValue(this.getSelected(this.projectTypeList, response.projectTypeCode));
                this.projectForm.get('cityId').setValue(this.getSelected(this.locationsList, response.cityId));
                this.projectForm.get('startDate').setValue(response.startDate);
                this.projectForm.get('endDate').setValue(response.endDate);
                this.projectForm
                    .get('offshoreManagerCode')
                    .setValue(this.getSelected(this.resourceManagerList, response.offshoreManagerCode));
                this.projectForm
                    .get('onshoreManagerCode')
                    .setValue(this.getSelected(this.resourceManagerList, response.onshoreManagerCode));
                this.projectForm.get('projectStatusCode').setValue(this.getSelected(this.statusList, response.projectStatusCode));

                this.technologies = response.technologyList;

                let self = this;
                this.technologies.map(index => {
                    const idx = self.technologyList.findIndex(x => x.value === index.value);
                    self.technologyList.splice(idx, 1);
                });

                this.projectForm.get('practices').setValue(response.practiceList);
                this.projectForm.disable();
                this.projectForm.get('technology').setValue('field');
            });
        });
    }

    enableEditMode() {
        this.isViewMode = false;
        this.projectForm.enable();
        this.projectForm.get('projectCode').disable();
        this.projectForm.get('accountCode').disable();
    }

    updateProjectDetails() {
        if (this.projectForm.valid) {
            const practiceList = this.practices;
            let datePipe = new DatePipe('en-US');
            const data = {
                projectCode: this.projectForm.get('projectCode').value,
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

            this.projectService.updateProjectDetails(data).subscribe(response => {
                this.isViewMode = true;
                this.projectForm.disable();
                this.showMessage = true;
                this.actionMessage = 'Changes Saved';
            });
        }
    }

    compareFn(c1: any, c2: any): boolean {
        return c1 && c2 ? c1.key === c2.key : c1 === c2;
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
        this.technologies.push({ key: event.option.value.key, value: event.option.value.value });
        let self = this;
        let index = self.technologyList.indexOf(event.option.value);
        this.technologyList.splice(index, 1);
        this.technologyInput.nativeElement.value = '';
        this.technologyCtrl.setValue(null);
        this.projectForm.get('technology').setValue('field');
    }

    getSelected(list, code): any {
        return list.find(index => {
            return index.key === code;
        });
    }
}
