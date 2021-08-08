import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MatDialogModule} from '@angular/material/dialog';
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {ToastrModule} from 'ngx-toastr';
import {KeycloakAngularModule, KeycloakService} from 'keycloak-angular';
import {MaterialModule} from './material.module';
import {MAT_RIPPLE_GLOBAL_OPTIONS, RippleGlobalOptions} from '@angular/material/core';
import {ServiceProvidersService} from './services/service.providers.service';
import {MatIconModule} from '@angular/material/icon';
import { BrowseComponent } from './browse/browse.component';
import { ShopComponent } from './shop/shop.component';
import { MenuComponent } from './menu/menu.component';
import { CartComponent } from './cart/cart.component';
import { ItemSelectComponent } from './item-select/item-select.component';
import {OrderService} from './services/order.service';
import { AccountSelectComponent } from './account-select/account-select.component';
import { AddAccountDialogComponent } from './add-account-dialog/add-account-dialog.component';


function initializeKeycloak(keycloak: KeycloakService): any {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080/auth',
        realm: 'springTest',
        clientId: 'serviceProvider',
      },
      initOptions: {
        onLoad: 'check-sso',
        checkLoginIframe: false
      },
      enableBearerInterceptor: true,
      bearerPrefix: 'Bearer',
    });
}

const globalRippleConfig: RippleGlobalOptions = {
  disabled: true,
  animation: {
    enterDuration: 300,
    exitDuration: 0
  }
};

@NgModule({
  declarations: [
    AppComponent,
    BrowseComponent,
    ShopComponent,
    MenuComponent,
    CartComponent,
    ItemSelectComponent,
    AccountSelectComponent,
    AddAccountDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    MatIconModule,
    FlexLayoutModule,
    MatDialogModule,
    FormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    KeycloakAngularModule,
  ],
  providers: [
    {provide: MAT_RIPPLE_GLOBAL_OPTIONS, useValue: globalRippleConfig},
    HttpClient,
    ServiceProvidersService,
    OrderService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
