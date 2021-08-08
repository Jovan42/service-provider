import {Component, OnInit} from '@angular/core';
import {DeliveryService} from '../services/delivery.service';

@Component({
  selector: 'app-delivery-people',
  templateUrl: './delivery-people.component.html',
  styleUrls: ['./delivery-people.component.scss']
})
export class DeliveryPeopleComponent implements OnInit {
  deliveryPeople = [];
  columnsToDisplay: string[] = ['id', 'First Name', 'Last Name', 'status'];
  constructor(private _deliveryService: DeliveryService) {
  }

  ngOnInit(): void {
    this._deliveryService.getAllDeliveryMan().subscribe(deliveryPeople => {
      this.deliveryPeople = deliveryPeople;
    });
  }

  getTableValue(element: any, column): string {
    switch (column) {
      case 'First Name':
        return element.firstName;
      case 'Last Name':
        return element.lastName;
      default:
        return element[column.toLowerCase()];

    }
  }
}
