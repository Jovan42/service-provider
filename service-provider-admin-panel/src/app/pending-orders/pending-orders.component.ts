import {Component, OnInit} from '@angular/core';
import {OrdersService} from '../services/orders.service';
import {ServiceProvidersService} from '../services/service.providers.service';
import {UtilFunctions} from '../utilFunctions';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-pending-orders',
  templateUrl: './pending-orders.component.html',
  styleUrls: ['./pending-orders.component.scss']
})
export class PendingOrdersComponent implements OnInit {
  preparationStartOrders: any;
  preparationFinishOrders: any;
  approvalOrders: any;
  items = new Map<number, string>();
  estimatedTime = {};

  constructor(private _ordersService: OrdersService, private serviceProvidersService: ServiceProvidersService, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.loadData();
  }

  calculatePrice(order: any): number {
    let price = 0;
    order.boughtItems.forEach(item => price += item.currentPricePerUnit * item.amount);
    return price;
  }

  getItemName(id: any): string {
    return this.items.get(id);
  }

  getItems(order: any) {
    order.boughtItems.forEach(item => {
      if (!this.items.get(item.menuItemId)) {
        this.serviceProvidersService.getMenuItem(item.menuItemId).subscribe(menuItem => {
          this.items.set(menuItem.id, menuItem.name);
        });
      }
    });
  }

  loadData(): void {
    this._ordersService.getPendingOrders('PENDING_MANUAL_SERVICE_PROVIDER_VALIDATION').subscribe(orders => {
      this.approvalOrders = orders;
    });
    this._ordersService.getPendingOrders('PENDING_PREPARATION').subscribe(orders => {
      this.preparationStartOrders = orders;
    });
    this._ordersService.getPendingOrders('IN_PREPARATION').subscribe(orders => {
      this.preparationFinishOrders = orders;
    });
  }

  approveOrder(order: any): void {
    this._ordersService.manuallyApprove(order.id).subscribe(value => {
      UtilFunctions.successSnackbar(this.snackBar, `Order [${order.id}] successfully aborted`);
      this.loadData();
    });
  }

  abort(order: any): void {
    this._ordersService.abort(order.id).subscribe(value => {
      UtilFunctions.successSnackbar(this.snackBar, `Order [${order.id}] successfully approved`);
      this.loadData();
    });
  }

  startOrderPreparation(order: any): void {
    if (this.estimatedTime[order.id]) {
      this._ordersService.startPreparation(order.id, this.estimatedTime[order.id]).subscribe(value => {
        UtilFunctions.successSnackbar(this.snackBar, `Preparation for order [${order.id}] successfully started`);
        this.loadData();
      });
    }
  }

  startFinishPreparation(order: any): void {
    this._ordersService.finishPreparation(order.id).subscribe(value => {
      UtilFunctions.successSnackbar(this.snackBar, `Preparation for order [${order.id}] successfully finished`);
      this.loadData();
    });
  }
}
