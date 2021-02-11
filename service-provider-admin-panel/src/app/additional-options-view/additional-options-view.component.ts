import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-additional-options-view',
  templateUrl: './additional-options-view.component.html',
  styleUrls: ['./additional-options-view.component.scss']
})
export class AdditionalOptionsViewComponent implements OnInit {
  groups = [{}, {}];

  constructor() { }

  ngOnInit(): void {
  }

  closeDialogAndSave() {

  }

  addGroup() {
    this.groups.push({});
  }
}
