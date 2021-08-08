import { Component, OnInit } from '@angular/core';
import {DeliveryService} from '../services/delivery.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UtilFunctions} from '../utilFunctions';

@Component({
  selector: 'app-deliveries',
  templateUrl: './deliveries.component.html',
  styleUrls: ['./deliveries.component.scss']
})
export class DeliveriesComponent implements OnInit {
  waitingToPickUp: any;
  waitingToBeDelivered: any;

  constructor(private _deliveryService: DeliveryService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.loadData();
  }

  pickUp(delivery: any) {
    this._deliveryService.pickUp(delivery.id).subscribe(value => {
      this.loadData();
      UtilFunctions.successSnackbar(this.snackBar, 'Delivery [id] succesfully picked up.');
    });
  }

  loadData() {
    this._deliveryService.getAllDeliveriesByStatus('READY_TO_PICK_UP').subscribe(value => {
      this.waitingToPickUp = value;
    });
    this._deliveryService.getAllDeliveriesByStatus('ON_THE_WAY').subscribe(value => {
      this.waitingToBeDelivered = value;
    });
  }

  deliver(delivery: any) {
    this._deliveryService.deliver(delivery.id).subscribe(value => {
      this.loadData();
      UtilFunctions.successSnackbar(this.snackBar, 'Delivery [id] succesfully delivered.');
    });
  }
}
