import {Component, OnInit} from '@angular/core';
import {UtilFunctions} from '../utilFunctions';
import {MatSnackBar} from '@angular/material/snack-bar';
import {OrderService} from '../services/order.service';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {AccountSelectComponent} from '../account-select/account-select.component';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  items: any[] = [];
  serviceProviderId;

  constructor(private snackBar: MatSnackBar, public dialog: MatDialog, private _orderService: OrderService, private route: ActivatedRoute) {
    this.serviceProviderId = this.route.snapshot.params.id;

  }

  ngOnInit(): void {
    const cartItems = localStorage.getItem('cartItems' + this.serviceProviderId);
    if (cartItems) {
      this.items = JSON.parse(cartItems);
    }
  }

  public addItem(item: any): void {
    this.items.push(item);
    localStorage.setItem('cartItems' + this.serviceProviderId, JSON.stringify(this.items));
    UtilFunctions.successSnackbar(this.snackBar, 'Item successfully added to cart');

  }

  public removeItem(item): void {
    this.items.splice(this.items.indexOf(item), 1);
    localStorage.setItem('cartItems', JSON.stringify(this.items));
    UtilFunctions.successSnackbar(this.snackBar, 'Item successfully removed to cart');

  }

  calculateTotal(): number {
    let sum = 0;
    this.items.forEach(item => sum += item.item.price * item.amount);
    return sum;
  }

  checkOut(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '40%';
    dialogConfig.data = {value :this.items, serviceProviderId: this.serviceProviderId };
    const accountSelectDialog = this.dialog.open(AccountSelectComponent, dialogConfig);
    accountSelectDialog.afterClosed().subscribe(result => {
      debugger
    });
  }
}
