import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {OrganisationComponent} from './organisation/organisation.component';
import {OrganisationsComponent} from './organisations/organisations.component';

const routes: Routes = [
  { path: 'organisations/:id', component: OrganisationComponent },
  { path: '', component: OrganisationsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
