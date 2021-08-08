import {MatSnackBar} from '@angular/material/snack-bar';

export  class UtilFunctions {
  public static successSnackbar(snackBar: MatSnackBar, message): void {
    snackBar.open(message, 'Close', {
      duration: 400000,
      panelClass: ['mat-toolbar', 'success-snackbar']
    });
  }

  public static errorSnackbar(snackBar: MatSnackBar, message): void {
    snackBar.open(message, 'Close', {
      duration: 4000,
      panelClass: ['mat-toolbar', 'error-snackbar']
    });
  }
}
