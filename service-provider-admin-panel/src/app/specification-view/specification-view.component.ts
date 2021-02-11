import {Component, Inject, OnInit} from '@angular/core';
import {Specification} from '../models/Organisation';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-specification-view',
  templateUrl: './specification-view.component.html',
  styleUrls: ['./specification-view.component.scss']
})
export class SpecificationViewComponent implements OnInit {
  private data;
  public specifications: Specification[] = [];
  constructor(private dialogRef: MatDialogRef<SpecificationViewComponent>, @Inject(MAT_DIALOG_DATA) data) {
    this.data = data;
  }

  ngOnInit(): void {
  }

  removeSpecification(specification: Specification): void {
    this.specifications.splice(this.specifications.indexOf(specification), 1);
  }

  addSpecification(): void {
    this.specifications.push({name: '', value: ''});
  }

  closeDialogAndSave(): void {
    this.dialogRef.close(this.specifications);
  }
}
