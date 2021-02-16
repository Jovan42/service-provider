import {Component, Inject, OnInit} from '@angular/core';
import {MenuItem} from '../models/ServiceProvider';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {ServiceProvidersService} from '../services/service.providers.service';
import {UtilFunctions} from '../utilFunctions';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-new-menu-item',
  templateUrl: './new-menu-item.component.html',
  styleUrls: ['./new-menu-item.component.scss']
})
export class NewMenuItemComponent implements OnInit {
  private data;
  menuItem: MenuItem = {};

  constructor(private dialogRef: MatDialogRef<NewMenuItemComponent>,
              @Inject(MAT_DIALOG_DATA) data,
              public dialog: MatDialog,
              private serviceProvidersService: ServiceProvidersService,
              private snackBar: MatSnackBar) {
    this.data = data;
  }

  ngOnInit(): void {
  }

  save(): void {
    this.serviceProvidersService.addMenuItem(this.data.id, this.menuItem).subscribe(result => {
      UtilFunctions.successSnackbar(this.snackBar, 'Menu Item successfully created');
      this.dialogRef.close();
    });
  }
}
