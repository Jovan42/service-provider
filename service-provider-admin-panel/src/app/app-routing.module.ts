import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {OrganisationComponent} from './organisation/organisation.component';

const routes: Routes = [
  { path: 'organisation', component: OrganisationComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
