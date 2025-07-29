import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.prod';
//export const BASE_URL = ["http://16.170.223.44:8082"]
export const BASE_URL = [`${environment.apiUrl}`]

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  register(signupRequest: any):Observable<any>{
    return this.http.post(BASE_URL+ "/api/auth/signup",signupRequest);
  }

  login(logInRequest: any) {
    return this.http.post<any>(BASE_URL+ '/api/auth/login', logInRequest);
  }
}