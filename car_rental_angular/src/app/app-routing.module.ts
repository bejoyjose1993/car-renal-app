import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './auth/components/signup/signup.component';
import { LoginComponent } from './auth/components/login/login.component';
import { CustomerDashboardComponent } from './modules/customer/components/customer-dashboard/customer-dashboard.component';
import { AuthGuard } from './auth/services/auth/auth.guard';
import { SearchCarComponent } from './modules/customer/components/search-car/search-car.component';

const routes: Routes = [
  { path: "", component: LoginComponent },
  { path: "register", component: SignupComponent },
  { path: "login", component: LoginComponent },
  { path: "dashboard", component: CustomerDashboardComponent, canActivate: [AuthGuard]  },
  { path: "search", component: SearchCarComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
