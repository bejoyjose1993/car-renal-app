import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrls: ['./my-bookings.component.scss']
})
export class MyBookingsComponent {
  bookings: any;
  displayedColumns: string[] = ['from', 'to', 'days', 'price', 'status'];
  constructor(private service: CustomerService){
    this.getMyBookings();
  }

  getMyBookings(){
    this.service.getBookingsByUserId().subscribe((res) =>{
      console.log(res);
      this.bookings = res;
    })
  }
}
