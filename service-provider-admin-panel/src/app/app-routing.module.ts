import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ServiceproviderComponent} from './serviceprovider/serviceprovider.component';
import {OrganisationsComponent} from './organisations/organisations.component';
import {PendingOrdersComponent} from './pending-orders/pending-orders.component';
import {DeliveryPeopleComponent} from './delivery-people/delivery-people.component';

const routes: Routes = [
  {path: 'organisations/:id', component: ServiceproviderComponent},
  {path: '', component: OrganisationsComponent},
  {path: 'pending', component: PendingOrdersComponent},
  {path: 'delivery', component: DeliveryPeopleComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
