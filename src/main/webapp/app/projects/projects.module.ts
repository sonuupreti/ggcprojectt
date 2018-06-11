import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { projectsState } from './projects.route';
import { MaterialModule } from '../material.module';
import { ListProjectsComponent } from './list-projects/list-projects.component';
import { ProjectService } from './projects.service';
import { AddProjectComponent } from './add-project/add-project.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ViewProjectComponent } from './view-project/view-project.component';
@NgModule({
    imports: [CommonModule, FormsModule, ReactiveFormsModule, MaterialModule, RouterModule.forChild(projectsState)],
    providers: [ProjectService],
    declarations: [ListProjectsComponent, AddProjectComponent, ViewProjectComponent]
})
export class ProjectsModule {}
