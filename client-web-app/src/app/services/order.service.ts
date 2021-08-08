import {Injectable} from '@angular/core';
import {ResourceService} from './resource.service';
import {Specification} from '../models/ServiceProvider';
import {Observable} from 'rxjs';

@Injectable()
export class OrderService {
  constructor(private resourceService: ResourceService) {
  }

  private baseUrl = 'http://localhost:8090';
  private accountingBaseUrl = 'http://localhost:8092';

  createOrder(order: any): Observable<any> {
    return this.resourceService.create(this.baseUrl + '/orders', order);
  }

  getAccounts(): Observable<any> {
    return this.resourceService.get(this.accountingBaseUrl + '/accounts');
  }

}
