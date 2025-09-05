import './vendor.ts';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';
import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { ${projectName}SharedModule } from 'app/shared';
import { ${projectName}CoreModule } from 'app/core';
import { ${projectName}AppRoutingModule } from './app-routing.module';
import { ${projectName}HomeModule } from './home/home.module';
import { ${projectName}AccountModule } from './account/account.module';
import { ${projectName}EntityModule } from './entities/entity.module';
import { ${projectName}DashboardModule } from './dashboard/dashboard.module';
import * as moment from 'moment';
import { JhiMainComponent, NavbarComponent, FooterComponent, LeftmenuComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        ${projectName}AppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: false,
            alertTimeout: 5000,
            i18nEnabled: true,
            defaultI18nLang: 'it'
        }),
        ${projectName}SharedModule.forRoot(),
        ${projectName}CoreModule,
        ${projectName}HomeModule,
        ${projectName}AccountModule,
        ${projectName}DashboardModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        ${projectName}EntityModule
    ],
    declarations: [JhiMainComponent, NavbarComponent, LeftmenuComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class ${projectName}AppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
