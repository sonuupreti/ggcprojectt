import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { addResourceRout } from './add-resource/add-resource.route';

const routes: Routes = [
    {
        path: '',
        children: [addResourceRout],
        data: {
            authorities: ['ROLE_ADMIN']
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ResourceRoutingModule {}
