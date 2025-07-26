import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { CustomerService } from '../../service/customer.service';
@Component({
  selector: 'app-search-car',
  templateUrl: './search-car.component.html',
  styleUrls: ['./search-car.component.scss']
})
export class SearchCarComponent {
  searchForm: FormGroup;
  carTypes: string[] = ['SUV', 'Sedan', 'Hatchback', 'Van']; // Add or modify as needed
  cars: any = [];
  selectedCar: any = null;
  pickupDate: any;
  dropoffDate: any;
  constructor(private fb: FormBuilder,
              private customerService: CustomerService) {
    this.searchForm = this.fb.group({
      carType: ['', Validators.required],
      pickupDate: ['', Validators.required],
      pickupTime: ['10:00', Validators.required],
      dropoffDate: ['', Validators.required],
      dropoffTime: ['10:00', Validators.required]
    });
  }
  
  searchCars() {
    this.cars = [];
    if (this.searchForm.valid) {
      const formValue = this.searchForm.value;
      const pickupTime = formValue.pickupTime.length === 5 ? formValue.pickupTime + ':00' : formValue.pickupTime;
      const dropoffTime = formValue.dropoffTime.length === 5 ? formValue.dropoffTime + ':00' : formValue.dropoffTime;
      const carType = formValue.carType;
  
      const pickupDateTime = `${formValue.pickupDate}T${pickupTime}`;
      const dropoffDateTime = `${formValue.dropoffDate}T${dropoffTime}`;
      this.pickupDate= pickupDateTime;
      this.dropoffDate= dropoffDateTime;
      this.customerService.getAvailableCars(carType,pickupDateTime,dropoffDateTime).subscribe((res) =>{
        res.forEach(element => {
          element.processedImg = 'data:image/jpeg;base64,' + element.image;
          this.cars.push(element);
        });
        if (this.cars.length > 0) {
          this.selectedCar = this.cars[0];
        }
      });
    }
  }

  selectCar(car: any) {
    this.selectedCar = car;
  }
}
