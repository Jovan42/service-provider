import {Injectable} from '@angular/core';
import {ResourceService} from './resource.service';
import {MenuItem, ServiceProvider, ServiceProviderListResponse, Specification} from '../models/ServiceProvider';
import {Observable} from 'rxjs';

@Injectable()
export class OrdersService {
  constructor(private resourceService: ResourceService) {
  }
  private baseUrl = 'http://localhost:8090';
  getPendingOrders(status): Observable<any> {
    return this.resourceService.get(`${this.baseUrl}/orders/status?status=${status}`);
  }

  abort(orderId): Observable<any> {
    return this.resourceService.update(`${this.baseUrl}/orders/${orderId}/abort`, {});
  }

  manuallyApprove(orderId): Observable<any> {
    return this.resourceService.update(`${this.baseUrl}/orders/${orderId}/manuallyApprove`, {});
  }

  startPreparation(orderId, preparationTimeInMinutes): Observable<any> {
    return this.resourceService.update(`${this.baseUrl}/orders/${orderId}/startPreparation`, {preparationTimeInMinutes});
  }

  finishPreparation(orderId): Observable<any> {
    return this.resourceService.update(`${this.baseUrl}/orders/${orderId}/finishPreparation`, {});
  }
}
