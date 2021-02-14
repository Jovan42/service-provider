import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {MenuItem} from '../models/ServiceProvider';
import {AdditionalOptionsViewComponent} from '../additional-options-view/additional-options-view.component';
import {SpecificationViewComponent} from '../specification-view/specification-view.component';
import {ServiceProvidersService} from '../services/service.providers.service';

@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.scss']
})
export class MenuItemComponent implements OnInit {
  private data;
  id;
  menuItem: MenuItem;

  constructor(private dialogRef: MatDialogRef<MenuItemComponent>,
              @Inject(MAT_DIALOG_DATA) data,
              public dialog: MatDialog,
              private serviceProvidersService: ServiceProvidersService) {
    this.data = data;
  }


  ngOnInit(): void {
    this.serviceProvidersService.getMenuItem(this.data.id).subscribe(item => {
      this.menuItem = item;
    });
  }

  save(): void {

  }

  openSpecifications(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '50%';
    const specificationViewComponentDialog = this.dialog.open(SpecificationViewComponent, dialogConfig);
    specificationViewComponentDialog.afterClosed().subscribe(result => {
      if (result) {
        this.menuItem.specifications = result;
      }
    });
  }

  openAdditionalOptions(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '50%';
    const specificationViewComponentDialog = this.dialog.open(AdditionalOptionsViewComponent, dialogConfig);
    specificationViewComponentDialog.afterClosed().subscribe(result => {
      this.menuItem.additionalRequirements = result;
    });
  }
}
