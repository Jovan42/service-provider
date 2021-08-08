import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-item-select',
  templateUrl: './item-select.component.html',
  styleUrls: ['./item-select.component.scss']
})
export class ItemSelectComponent implements OnInit {
  item: any;
  amount = 0;

  constructor(
    @Inject(MAT_DIALOG_DATA) data,
    private dialogRef: MatDialogRef<ItemSelectComponent>,
  ) {
    this.item = data;
  }

  ngOnInit(): void {
  }

  addItem(): void {
    if (this.amount && this.amount > 0) {
      this.dialogRef.close({item: this.item, amount: this.amount});
    } else {
      this.dialogRef.close();
    }
  }
}
