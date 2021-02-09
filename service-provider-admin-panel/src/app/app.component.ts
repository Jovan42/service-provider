import {Component, ViewChild} from '@angular/core';
import {MatSidenav} from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'service-provider-admin-panel';
  sidenavOpened = false;
  @ViewChild('sidenav', {static: true}) sidenav: MatSidenav;

  toggle(): void {
    const matDrawerToggleResultPromise = this.sidenav.toggle();
    this.sidenavOpened = !this.sidenavOpened;
  }
}
