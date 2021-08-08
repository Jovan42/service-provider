import {Component, OnInit, ViewChild} from '@angular/core';
import {CartComponent} from '../cart/cart.component';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss']
})
export class ShopComponent implements OnInit {
  @ViewChild('cart', {static: true}) cart: CartComponent;
  item;
  constructor() { }

  ngOnInit(): void {
  }

  public addItem(event) {
    this.cart.addItem(event);
  }

}
