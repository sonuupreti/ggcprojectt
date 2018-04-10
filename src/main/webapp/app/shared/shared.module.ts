import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { Itrack2SharedLibsModule, Itrack2SharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [Itrack2SharedLibsModule, Itrack2SharedCommonModule],
  declarations: [HasAnyAuthorityDirective],
  providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
  exports: [Itrack2SharedCommonModule, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Itrack2SharedModule {}
