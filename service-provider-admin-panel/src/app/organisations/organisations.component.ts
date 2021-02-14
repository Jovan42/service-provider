import {Component, OnInit} from '@angular/core';
import {ServiceProvidersService} from '../services/service.providers.service';
import {ServiceProvider} from '../models/ServiceProvider';

@Component({
  selector: 'app-organisations',
  templateUrl: './organisations.component.html',
  styleUrls: ['./organisations.component.scss']
})
export class OrganisationsComponent implements OnInit {
  organisations: ServiceProvider[] = [];

  constructor(private serviceProvidersService: ServiceProvidersService) {
  }

  ngOnInit(): void {
    this.serviceProvidersService.getServiceProviders().subscribe(response => {
      this.organisations = response.content;
    });
  }

}
