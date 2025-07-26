import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';

@Component({
  selector: 'app-customer-dashboard',
  templateUrl: './customer-dashboard.component.html',
  styleUrls: ['./customer-dashboard.component.scss']
})
export class CustomerDashboardComponent {

  constructor(private customerService: CustomerService){}
  cars: any = [];
  selectedCar: any = null;

  ngOnInit(){
    this.getAllCars();
  }

  getAllCars(){
    this.customerService.getAllCArs().subscribe((res) =>{
      res.forEach(element => {
          element.processedImg = 'data:image/jpeg;base64,' + element.image;
          this.cars.push(element);
      });
      if (this.cars.length > 0) {
        this.selectedCar = this.cars[0];
      }
    });
  }

  selectCar(car: any) {
    this.selectedCar = car;
  }
}
