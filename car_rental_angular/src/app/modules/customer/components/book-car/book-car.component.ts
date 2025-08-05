import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { StorageService } from 'src/app/auth/services/storage/storage.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrls: ['./book-car.component.scss'],
  providers: [DatePipe]
})
export class BookCarComponent {

  carId:number = this.activatedRoute.snapshot.params["id"];
  car: any;
  validateForm: FormGroup;
  dateFormat:"yyyy-MM-dd";
  constructor(private service:CustomerService,
              private activatedRoute: ActivatedRoute,
              private fb: FormBuilder,
              private router: Router,
              private snackBar: MatSnackBar,
              private storageService: StorageService,
              private datePipe: DatePipe){}

  ngOnInit(){
    this.getCarById()
  }

  getCarById(){
    this.service.getCarById(this.carId).subscribe((res) => {
      this.car = res;
    })
  }

  bookACar(data: any){
    const formattedFromDate = this.activatedRoute.snapshot.queryParamMap.get('pickupDate');
    const formattedToDate = this.activatedRoute.snapshot.queryParamMap.get('dropoffDate');;
    let bookACarDto = {
      toDate: formattedToDate,
      fromDate: formattedFromDate,
      userId: StorageService.getUserId(),
      carId: this.carId
    }
    this.service.bookCar(bookACarDto).subscribe((res) =>{
        this.snackBar.open('Booking Request Submitted Successfully!', 'Close', {
          duration: 5000, // milliseconds
          panelClass: ['success-snackbar']
        });
        setTimeout(() => {
          this.router.navigateByUrl("/my_bookings");
        }, 1000);
    }, error => {
      this.snackBar.open('Something Went Wrong.', 'Close', {
        duration: 5000,
        panelClass: ['error-snackbar']
      });
    });

  }
}
