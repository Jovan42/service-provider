import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {OrderService} from '../services/order.service';
import {ActivatedRoute} from '@angular/router';
import {UtilFunctions} from '../utilFunctions';
import {MatSnackBar} from '@angular/material/snack-bar';
import {KeycloakService} from 'keycloak-angular';

@Component({
  selector: 'app-account-select',
  templateUrl: './account-select.component.html',
  styleUrls: ['./account-select.component.scss']
})
export class AccountSelectComponent implements OnInit {
  items: any[] = [];
  accounts: any[] = [];
  serviceProviderId;
  selectedAccount;

  constructor(@Inject(MAT_DIALOG_DATA) data,
              private dialogRef: MatDialogRef<AccountSelectComponent>,
              private _orderService: OrderService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private _keycloakService: KeycloakService) {
    this.items = data.value;
    this.serviceProviderId = data.serviceProviderId;
  }

  ngOnInit(): void {
    this._orderService.getAccounts().subscribe(result => {
      this.accounts = result;
    });
  }

  calculateTotal(): number {
    let sum = 0;
    this.items.forEach(item => sum += item.item.price * item.amount);
    return sum;
  }

  makeOrder() {
    this._orderService.createOrder(this.createOrderModel()).subscribe(result => {
      UtilFunctions.successSnackbar(this.snackBar, 'Order successfully created');
    });
  }

  createOrderModel() {
    debugger
    const boughtItems = this.items.map(item => {
      return {
        menuItemId: item.item.id,
        currentPricePerUnit: item.item.price,
        amount: item.amount
      };
    });
    return {
      serviceProviderId: this.serviceProviderId,
      boughtItems,
      accountId: this.selectedAccount,
    };
  }
}
