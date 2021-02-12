import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {Organisation} from '../models/Organisation';
import {AdditionalOptionsViewComponent} from '../additional-options-view/additional-options-view.component';
import {SpecificationViewComponent} from '../specification-view/specification-view.component';

@Component({
  selector: 'app-organisation-vew',
  templateUrl: './organisation-vew.component.html',
  styleUrls: ['./organisation-vew.component.scss']
})
export class OrganisationVewComponent implements OnInit {
  private data;
  specifications = [{}, {}];

  constructor(private dialogRef: MatDialogRef<OrganisationVewComponent>, @Inject(MAT_DIALOG_DATA) data, public dialog: MatDialog) {
    this.data = data;
  }

  public organisation: Organisation;

  ngOnInit(): void {
    this.setupEmptyOrganisation();
  }

  save(): void {

  }

  setupEmptyOrganisation(): void {
    this.organisation = {
      address: '',
      description: '',
      email: '',
      name: '',
      specifications: [],
      additionalRequirements: [],
      showSpecification: true,
      showAdditionalRequirements: true
    };
  }

  openSpecifications(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '50%';
    const specificationViewComponentDialog = this.dialog.open(SpecificationViewComponent, dialogConfig);
    specificationViewComponentDialog.afterClosed().subscribe(result => {
      if (result) {
        this.organisation.specifications = result;
      }
    });
  }

  openAdditionalOptions(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '50%';
    const specificationViewComponentDialog = this.dialog.open(AdditionalOptionsViewComponent, dialogConfig);
    specificationViewComponentDialog.afterClosed().subscribe(result => {
      this.organisation.additionalRequirements = result;
    });
  }
}
