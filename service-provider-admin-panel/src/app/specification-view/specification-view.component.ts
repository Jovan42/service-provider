import {Component, Inject, OnInit} from '@angular/core';
import {Specification} from '../models/ServiceProvider';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ServiceProvidersService} from '../services/service.providers.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UtilFunctions} from '../utilFunctions';

@Component({
  selector: 'app-specification-view',
  templateUrl: './specification-view.component.html',
  styleUrls: ['./specification-view.component.scss']
})
export class SpecificationViewComponent implements OnInit {
  private data;
  public specifications: Specification[] = [];

  constructor(private dialogRef: MatDialogRef<SpecificationViewComponent>,
              @Inject(MAT_DIALOG_DATA) data,
              private serviceProvidersService: ServiceProvidersService,
              private snackBar: MatSnackBar) {
    this.data = data;
  }

  ngOnInit(): void {
    this.serviceProvidersService.getSpecifications(this.data.id).subscribe(specifications => {
      this.specifications = specifications;
    });
  }

  removeSpecification(specification: Specification): void {
    this.specifications.splice(this.specifications.indexOf(specification), 1);
  }

  addSpecification(): void {
    this.specifications.push({name: '', value: ''});
  }

  closeDialogAndSave(): void {
    this.serviceProvidersService.saveSpecifications(this.data.id, this.specifications).subscribe(result => {
      UtilFunctions.successSnackbar(this.snackBar, 'Specification successfully saved');
      this.dialogRef.close(this.specifications);
    });
  }
}
