import { Component, OnInit } from '@angular/core';
import {ServiceProvider} from '../models/ServiceProvider';
import {ServiceProvidersService} from '../services/service.providers.service';

@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.scss']
})
export class BrowseComponent implements OnInit {
  serviceProviders: ServiceProvider[] = [];

  constructor(private serviceProvidersService: ServiceProvidersService) {
  }

  ngOnInit(): void {
    this.serviceProvidersService.getServiceProviders().subscribe(response => {
      this.serviceProviders = response.content;
    });
  }

}
