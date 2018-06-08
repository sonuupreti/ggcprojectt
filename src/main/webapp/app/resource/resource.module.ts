import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';

import { ResourceRoutingModule } from './resource-routing.module';
import { AddResourceComponent } from './add-resource/add-resource.component';

//services
import { ResourceService } from './resource.service';

@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, MaterialModule, FlexLayoutModule, ResourceRoutingModule],
    declarations: [AddResourceComponent],
    providers: [ResourceService]
})
export class ResourceModule {}
