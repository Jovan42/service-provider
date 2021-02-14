import {NgModule} from '@angular/core';
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
    OrganisationsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    MatDialogModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    {provide: MAT_RIPPLE_GLOBAL_OPTIONS, useValue: globalRippleConfig},
    HttpClient,
    ServiceProvidersService

  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
