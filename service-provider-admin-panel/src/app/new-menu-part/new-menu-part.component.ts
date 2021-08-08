import {Component, Inject, OnInit} from '@angular/core';
import {MenuPart} from '../models/ServiceProvider';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {ServiceProvidersService} from '../services/service.providers.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-new-menu-part',
  templateUrl: './new-menu-part.component.html',
  styleUrls: ['./new-menu-part.component.scss']
})
export class NewMenuPartComponent implements OnInit {

  private data;
  menuPart: MenuPart = {};

  constructor(private dialogRef: MatDialogRef<NewMenuPartComponent>,
              @Inject(MAT_DIALOG_DATA) data,
              public dialog: MatDialog,
              private serviceProvidersService: ServiceProvidersService,
              private snackBar: MatSnackBar) {
    this.data = data;
  }

  ngOnInit(): void {
  }

  addMenuPart() {

  }
}
