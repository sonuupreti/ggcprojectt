import { NgModule } from '@angular/core';

import { Itrack2SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [Itrack2SharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  providers: [],
  exports: [Itrack2SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class Itrack2SharedCommonModule {}
