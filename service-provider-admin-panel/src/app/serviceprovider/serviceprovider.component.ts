import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {MenuItemComponent} from '../organisation-vew/menu-item.component';
import {ServiceProvidersService} from '../services/service.providers.service';
import {ActivatedRoute} from '@angular/router';
import {MenuPart, ServiceProvider} from '../models/ServiceProvider';
import {ToastrService} from 'ngx-toastr';
import {NewMenuItemComponent} from '../new-menu-item/new-menu-item.component';
import {NewMenuPartComponent} from '../new-menu-part/new-menu-part.component';

@Component({
  selector: 'app-serviceprovider',
  templateUrl: './serviceprovider.component.html',
  styleUrls: ['./serviceprovider.component.scss']
})
export class ServiceproviderComponent implements OnInit {
  columnsToDisplay: string[] = ['Name', 'Description', 'Price'];
  serviceProviderId: number;
  serviceProvider: ServiceProvider = {address: '', description: '', email: '', name: '', manualApprovalRequired: true};

  constructor(public dialog: MatDialog, private serviceProvidersService: ServiceProvidersService, private route: ActivatedRoute, private toastr: ToastrService) {
    this.serviceProviderId = this.route.snapshot.params.id;
  }

  ngOnInit(): void {
    this.serviceProvidersService.getServiceProviderById(this.serviceProviderId).subscribe(response => {
      this.serviceProvider = response;
    });
    this.toastr.success('Hello world!', 'Toastr fun!');
  }

  rowClicked(row): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '80%';
    dialogConfig.data = row;
    this.dialog.open(MenuItemComponent, dialogConfig);
  }

  addMenuItem(menuPart: MenuPart): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '80%';
    dialogConfig.data = menuPart;
    const specificationViewComponentDialog = this.dialog.open(NewMenuItemComponent, dialogConfig);
    specificationViewComponentDialog.afterClosed().subscribe(result => {
      this.serviceProvidersService.getServiceProviderById(this.serviceProviderId).subscribe(response => {
        this.serviceProvider = response;
      });
    });
  }

  addMenuGroup(serviceProvider: ServiceProvider): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '80%';
    dialogConfig.data = serviceProvider;
    const specificationViewComponentDialog = this.dialog.open(NewMenuPartComponent, dialogConfig);
    specificationViewComponentDialog.afterClosed().subscribe(result => {
      this.serviceProvidersService.getServiceProviderById(this.serviceProviderId).subscribe(response => {
        this.serviceProvider = response;
      });
    });
  }
}
