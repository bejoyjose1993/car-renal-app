import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookCarComponent } from './components/book-car/book-car.component';
import { MyBookingsComponent } from './components/my-bookings/my-bookings.component';
import { AuthGuard } from 'src/app/auth/services/auth/auth.guard';

const routes: Routes = [
   {path: "book/:id", component: BookCarComponent, canActivate: [AuthGuard] },
   {path: "my_bookings", component: MyBookingsComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
