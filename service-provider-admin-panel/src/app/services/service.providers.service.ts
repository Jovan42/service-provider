import {Injectable} from '@angular/core';
import {ResourceService} from './resource.service';
import {MenuItem, ServiceProvider, ServiceProviderListResponse, Specification} from '../models/ServiceProvider';
import {Observable} from 'rxjs';

@Injectable()
export class ServiceProvidersService {
  constructor(private resourceService: ResourceService) {
  }
  private baseUrl = 'http://localhost:8080';
  getServiceProviders(): Observable<ServiceProviderListResponse> {
    return this.resourceService.get(this.baseUrl + '/serviceProviders');
  }

  getServiceProviderById(id): Observable<ServiceProvider> {
    return this.resourceService.get(this.baseUrl + '/serviceProviders/' + id);
  }

  getMenuItem(id): Observable<MenuItem> {
    return this.resourceService.get(this.baseUrl + '/menuItems/' + id);
  }

  getSpecifications(id: number): Observable<Specification[]> {
    return this.resourceService.get(this.baseUrl + '/menuItems/' + id + '/specifications');
  }

  saveSpecifications(id: number, specifications: Specification[]): Observable<Specification[]> {
    return this.resourceService.create(this.baseUrl + '/menuItems/' + id + '/specifications', specifications);
  }

  addMenuItem(menuPartId: number, menuItem: MenuItem): Observable<Specification[]> {
    return this.resourceService.create(this.baseUrl + '/menuParts/' + menuPartId + '/menuItems', menuItem);
  }

}
