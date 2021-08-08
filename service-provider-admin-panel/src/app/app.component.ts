import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSidenav} from '@angular/material/sidenav';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import {OrdersService} from './services/orders.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  username: string;
  title = 'service-provider-admin-panel';
  sidenavOpened = false;
  @ViewChild('sidenav', {static: true}) sidenav: MatSidenav;
  pendingOrderSize = 0;

  constructor(private _keycloakService: KeycloakService, private _ordersService: OrdersService) {
    _keycloakService.isLoggedIn()
      .then(value => {
        if (value) {
          console.log('signed in');
          _keycloakService.loadUserProfile()
            .then((keycloakProfile: KeycloakProfile) => {
              this.username = keycloakProfile.username;
            })
            .catch(error => {
              console.log('error1', error);
            });
        } else {
          _keycloakService.login()
            .then(() => {
              console.log('ssdfsdfs');
            })
            .catch(error => {
              console.log('error1', error);
            });
        }
        console.log(value);
      })
      .catch(error => {
        console.log('error', error);
      });
  }

  toggle(): void {
    const matDrawerToggleResultPromise = this.sidenav.toggle();
    this.sidenavOpened = !this.sidenavOpened;
  }

  ngOnInit(): void {
    this._ordersService.getPendingOrders('PENDING_MANUAL_SERVICE_PROVIDER_VALIDATION').subscribe(orders => {
      this.pendingOrderSize = orders.length;
    });
  }

  onSignout(): void {
    this._keycloakService.logout()
      .then(() => {
        console.log('ssdfsdfs');
      })
      .catch(error => {
        console.log('error1', error);
      });
  }

  logout(): void {
    this._keycloakService.logout();
  }
}
