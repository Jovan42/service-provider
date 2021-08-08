import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BrowseComponent} from './browse/browse.component';
import {ShopComponent} from './shop/shop.component';

const routes: Routes = [
  {path: 'browse', component: BrowseComponent},
  {path: 'browse/:id', component: ShopComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
