import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
const TOKEN = "token";
const USER = "user";
@Injectable({
  providedIn: 'root'
})
export class StorageService {
  private static isLoggedInSubject = new BehaviorSubject<boolean>(StorageService.isCustomerLoggedIn());
  static isLoggedIn$ = StorageService.isLoggedInSubject.asObservable();
  constructor() { }

  static saveToken(token: string): void {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN, token);
  }

  static saveUser(user: any): void {
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  static getToken(){
    return window.localStorage.getItem(TOKEN);
  }

  static getUser(): any {
    return window.localStorage.getItem(USER);
  }

  static getUserId(): any {
    const user = JSON.parse(this.getUser());
    if(user == null) { return '';}
    return user.id;
  }

  static getUserRole(): any {
    const user= JSON.parse(this.getUser());
    if(user == null) return "";
    return user.role;
  }

  static isCustomerLoggedIn(): boolean{
    if(this.getToken() == null) return false;
    const role : string = this.getUserRole();
    return role =="Customer";
  }

  
  static logout(): void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
    StorageService.isLoggedInSubject.next(false);
  }

  static login(): void{
    StorageService.isLoggedInSubject.next(true);
  }
}
