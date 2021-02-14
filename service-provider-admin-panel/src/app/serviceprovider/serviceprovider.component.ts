import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {MenuItemComponent} from '../organisation-vew/menu-item.component';
import {ServiceProvidersService} from '../services/service.providers.service';
import {ActivatedRoute} from '@angular/router';
import {ServiceProvider} from '../models/ServiceProvider';

@Component({
  selector: 'app-serviceprovider',
  templateUrl: './serviceprovider.component.html',
  styleUrls: ['./serviceprovider.component.scss']
})
export class ServiceproviderComponent implements OnInit {
  columnsToDisplay: string[] = ['Name', 'Description', 'Price'];
  serviceProviderId: number;
  serviceProvider: ServiceProvider = {address: '', description: '', email: '', name: '', manualApprovalRequired: true};
  constructor(public dialog: MatDialog, private serviceProvidersService: ServiceProvidersService, private route: ActivatedRoute) {
    this.serviceProviderId = this.route.snapshot.params.id;
  }

  ngOnInit(): void {
    this.serviceProvidersService.getServiceProviderById(this.serviceProviderId).subscribe(response => {
      this.serviceProvider = response;
    });
  }

  rowClicked(row): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '80%';
    dialogConfig.data = row;
    this.dialog.open(MenuItemComponent, dialogConfig);
  }
}
