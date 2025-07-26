import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BASE_URL } from 'src/app/auth/services/auth/auth.service';
import { StorageService } from 'src/app/auth/services/storage/storage.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  getAllCArs():Observable<any> {
    return this.http.get(BASE_URL + "/api/customer/cars",{
      headers: this.createAuthorizationHeader()
    })
  }

  getCarById(carId: number):Observable<any> {
    return this.http.get(BASE_URL + "/api/customer/cars/"+ carId,{
      headers: this.createAuthorizationHeader()
    })
  }

  getAvailableCars(carType: string, fromDate: string, toDate: string):Observable<any> {
    const params = new HttpParams()
                      .set('type', carType)
                      .set('from', fromDate)
                      .set('to', toDate)
    return this.http.get(BASE_URL + "/api/customer/search?"+ params, {
      headers: this.createAuthorizationHeader()
    })
  }
  
  getBookingsByUserId():Observable<any> {
    return this.http.get(BASE_URL + "/api/customer/cars/bookings/"+ StorageService.getUserId(),{
      headers: this.createAuthorizationHeader()
    })
  }

  bookCar(bookCarDto: any):Observable<any> {
    return this.http.post(BASE_URL + "/api/customer/car/book", bookCarDto,{
      headers: this.createAuthorizationHeader(),
    })
  }
  register(signupRequest: any):Observable<any>{
    return this.http.post(BASE_URL+ "/api/auth/signup",signupRequest);
  }

  createAuthorizationHeader(): HttpHeaders{
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization',
      'Bearer ' + StorageService.getToken()
    )
  }
}
