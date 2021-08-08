import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MAT_RIPPLE_GLOBAL_OPTIONS, RippleGlobalOptions} from '@angular/material/core';
import {MaterialModule} from './material.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {ServiceproviderComponent} from './serviceprovider/serviceprovider.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MenuItemComponent} from './organisation-vew/menu-item.component';
import {SpecificationViewComponent} from './specification-view/specification-view.component';
import {AdditionalOptionsViewComponent} from './additional-options-view/additional-options-view.component';
import {FormsModule} from '@angular/forms';
import {OrganisationsComponent} from './organisations/organisations.component';
import {ServiceProvidersService} from './services/service.providers.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {ToastrModule} from 'ngx-toastr';
import {NewMenuItemComponent} from './new-menu-item/new-menu-item.component';
import {NewMenuPartComponent} from './new-menu-part/new-menu-part.component';
import {KeycloakAngularModule, KeycloakService} from 'keycloak-angular';
import {OrdersService} from './services/orders.service';
import { PendingOrdersComponent } from './pending-orders/pending-orders.component';
import { DeliveryPeopleComponent } from './delivery-people/delivery-people.component';
import {DeliveryService} from './services/delivery.service';
import { DeliveriesComponent } from './deliveries/deliveries.component';

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
    ServiceproviderComponent,
    MenuItemComponent,
    SpecificationViewComponent,
    AdditionalOptionsViewComponent,
    OrganisationsComponent,
    NewMenuItemComponent,
    NewMenuPartComponent,
    PendingOrdersComponent,
    DeliveryPeopleComponent,
    DeliveriesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
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
    OrdersService,
    DeliveryService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
