import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ServiceproviderComponent} from './serviceprovider/serviceprovider.component';
import {OrganisationsComponent} from './organisations/organisations.component';

const routes: Routes = [
  {path: 'organisations/:id', component: ServiceproviderComponent},
  {path: '', component: OrganisationsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
