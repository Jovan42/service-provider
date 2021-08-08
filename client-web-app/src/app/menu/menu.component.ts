import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ServiceProvidersService} from '../services/service.providers.service';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {ItemSelectComponent} from '../item-select/item-select.component';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  serviceProviderId: number;
  menu: any;
  @Output() addItem: EventEmitter<any> = new EventEmitter();

  constructor(private route: ActivatedRoute,  private serviceProvidersService: ServiceProvidersService, public dialog: MatDialog) {
    this.serviceProviderId = this.route.snapshot.params.id;
  }

  ngOnInit(): void {
    this.serviceProvidersService.getServiceProviderById(this.serviceProviderId).subscribe(shop => {
      this.menu = shop.menuParts;
    });
  }


  itemSelected(item: any): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '40%';
    dialogConfig.data = item;
    const specificationViewComponentDialog = this.dialog.open(ItemSelectComponent, dialogConfig);
    specificationViewComponentDialog.afterClosed().subscribe(result => {
      if (result) {
        this.addItem.emit(result);
      }
    });
  }
}
