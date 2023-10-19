import { Injectable } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  constructor(private snackBar:MatSnackBar) { }

    openSuccessSnackBar(message: string, action: string) {
        this.snackBar.open(message, '', {
            horizontalPosition: 'center',
            verticalPosition: 'top',
            duration: 2000,
            panelClass: ['green-snackbar'],
        });
    }

    openErrorSnackBar(message: string, action: string) {
        this.snackBar.open(message, '', {
            horizontalPosition: 'center',
            verticalPosition: 'top',
            duration: 2000,
            panelClass: ['red-snackbar'],
        });
    }
}

