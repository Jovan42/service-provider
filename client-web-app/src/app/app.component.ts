import {Component, ViewChild} from '@angular/core';
import {MatSidenav} from '@angular/material/sidenav';
import {KeycloakService} from 'keycloak-angular';
import {KeycloakProfile} from 'keycloak-js';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'client-web-app';
  username: string;
  sidenavOpened = false;
  @ViewChild('sidenav', {static: true}) sidenav: MatSidenav;

  constructor(private _keycloakService: KeycloakService) {
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

  openAddAccountDialog() {

  }
}
