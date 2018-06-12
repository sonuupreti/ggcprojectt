import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';
import { MaterialModule } from './material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { Itrack2SharedModule } from 'app/shared';
import { Itrack2CoreModule } from 'app/core';
import { Itrack2AppRoutingModule } from './app-routing.module';
import { Itrack2HomeModule } from './home/home.module';
import { Itrack2EntityModule } from './entities/entity.module';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { StateStorageService } from 'app/core/auth/state-storage.service';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, ProfileService, PageRibbonComponent, ErrorComponent } from './layouts';

import { ListAccountsComponent } from './accounts/list-accounts/list-accounts.component';
import { ListAccountsService } from './accounts/list-accounts/account.service';
import { AddAccountComponent } from './accounts/add-account/add-account.component';
import { AddAccountService } from './accounts/add-account/add-account.service';
import { ViewAccountService } from './accounts/view-account/view-account.service';
import { ViewAccountComponent } from './accounts/view-account/view-account.component';
import { RemoveAccountConfirmDialogComponent } from './dialog/remove-account-confirm-dialog/remove-account-confirm-dialog.component';

@NgModule({
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MaterialModule,
        Itrack2AppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        Itrack2SharedModule,
        Itrack2CoreModule,
        Itrack2HomeModule,
        Itrack2EntityModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent,
        ListAccountsComponent,
        AddAccountComponent,
        ViewAccountComponent,
        RemoveAccountConfirmDialogComponent
    ],
    providers: [
        ProfileService,
        ListAccountsService,
        AddAccountService,
        ViewAccountService,
        PaginationConfig,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [StateStorageService, Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    entryComponents: [RemoveAccountConfirmDialogComponent],
    bootstrap: [JhiMainComponent]
})
export class Itrack2AppModule {}
