import {Injectable} from '@angular/core';
import {ResourceService} from './resource.service';
import {MenuItem, ServiceProvider, ServiceProviderListResponse, Specification} from '../models/ServiceProvider';
import {Observable} from 'rxjs';

@Injectable()
export class DeliveryService {
  constructor(private resourceService: ResourceService) {
  }
  private baseUrl = 'http://localhost:8093';

  getAllDeliveryMan(): Observable<any> {
    return this.resourceService.get(`${this.baseUrl}/deliveryPeople`);
  }
}
