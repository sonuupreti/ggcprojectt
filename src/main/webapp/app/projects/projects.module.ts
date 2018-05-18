import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { projectsState } from './projects.route';
import { MaterialModule } from '../material.module';
import { ListProjectsComponent } from './list-projects/list-projects.component';
import { ListProjectsService } from './list-projects/list-projects.service';
import { AddProjectComponent } from './add-project/add-project.component';
import { FlexLayoutModule } from '@angular/flex-layout';
@NgModule({
    imports: [CommonModule, MaterialModule, FlexLayoutModule, RouterModule.forChild(projectsState)],
    providers: [ListProjectsService],
    declarations: [ListProjectsComponent, AddProjectComponent]
})
export class ProjectsModule {}
