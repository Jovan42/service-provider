import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {OrganisationVewComponent} from '../organisation-vew/organisation-vew.component';
import {SpecificationViewComponent} from '../specification-view/specification-view.component';

const ELEMENT_DATA = [
  {id: 1, name: 'Pun oval', price: 1190, description: '1 kilogram. Ćevapi, kobasice, ražnjići, punjene pljeskavice, belo pileće meso, dimljena slanina, 400 grama pomfrita, 100 grama premaza po izboru, 100 grama luka.'},
  {id: 2, name: 'Mala Pljeskavica', price: 180, description: '110g'},
  {id: 3, name: 'Velika Pljeskavica', price: 310, description: '310g'},
  {id: 4, name: 'Pljeskavica sa topljenim sirom', price: 210, description: ''},
  {id: 5, name: 'Špikovana pljeskavica', price: 230, description: 'Okrugla pljeskavica sa sirom, šunkom i slaninom, 150g.'},
  {id: 6, name: 'Punjena pljeskavica', price: 230, description: 'Preklopljena pljeskavica sa sirom, šunkom i slaninom, 150g.'},
  {id: 7, name: 'Hit pljeska', price: 230, description: 'Pileća i praška šunka sa sirom između dve pljeskavice, preko 2 kom. slanine, 150g.'},
];


@Component({
  selector: 'app-organisation',
  templateUrl: './organisation.component.html',
  styleUrls: ['./organisation.component.scss']
})
export class OrganisationComponent implements OnInit {
  columnsToDisplay: string[] = ['Name', 'Description', 'Price'];
  data = ELEMENT_DATA;
  menuParts = [{}, {}];

  constructor(public dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  rowClicked(row): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '80%';
    dialogConfig.data = row;
    this.dialog.open(OrganisationVewComponent, dialogConfig);
  }
}
