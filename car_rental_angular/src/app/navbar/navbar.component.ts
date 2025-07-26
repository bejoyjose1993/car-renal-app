import { Component } from '@angular/core';
import { StorageService } from '../auth/services/storage/storage.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  isCustomerLoggedIn:boolean;
  private authSubscription!: Subscription;
  constructor(private router: Router){}

  ngOnInit(){
    this.authSubscription = StorageService.isLoggedIn$.subscribe(
      (status) => {
        this.isCustomerLoggedIn = status;
      }
    );
  }


  ngOnDestroy() {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }


  logout(){
    StorageService.logout();
    this.isCustomerLoggedIn = StorageService.isCustomerLoggedIn();
    this.router.navigateByUrl("/login");
  }
}
