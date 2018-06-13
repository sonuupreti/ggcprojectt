import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';

import { ResourceRoutingModule } from './resource-routing.module';
import { AddResourceComponent } from './add-resource/add-resource.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
//services
import { ResourceService } from './resource.service';
import { ViewResourceComponent } from './view-resource/view-resource.component';

@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, MaterialModule, ResourceRoutingModule, NgbModule.forRoot()],
    declarations: [AddResourceComponent, ViewResourceComponent],
    providers: [ResourceService]
})
export class ResourceModule {}
