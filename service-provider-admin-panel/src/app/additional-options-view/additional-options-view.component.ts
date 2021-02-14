import {Component, Inject, OnInit} from '@angular/core';
import {AdditionalRequirement, AdditionalRequirementsGroup} from '../models/ServiceProvider';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-additional-options-view',
  templateUrl: './additional-options-view.component.html',
  styleUrls: ['./additional-options-view.component.scss']
})
export class AdditionalOptionsViewComponent implements OnInit {
  groups: AdditionalRequirementsGroup[] = [];

  constructor(private dialogRef: MatDialogRef<AdditionalOptionsViewComponent>, @Inject(MAT_DIALOG_DATA) data) {
  }

  ngOnInit(): void {
  }

  addGroup(): void {
    const newGroup: AdditionalRequirementsGroup = {
      description: '',
      maxSelected: 0,
      minSelected: 0,
      name: '',
      requirements: []
    };
    this.groups.push(newGroup);
  }

  removeRequirement(requirement: AdditionalRequirement, requirements: AdditionalRequirement[]): void {
    requirements.splice(requirements.indexOf(requirement), 1);
  }

  addRequirements(groupToEdit: AdditionalRequirementsGroup): void {
    groupToEdit.requirements.push({name: '', price: 0});
  }

  removeGroup(groupToDelete: AdditionalRequirementsGroup, e: MouseEvent): void {
    e.preventDefault();
    this.groups.splice(this.groups.indexOf(groupToDelete), 1);
  }

  closeDialogAndSave(): void {
    this.dialogRef.close(this.groups);
  }
}
