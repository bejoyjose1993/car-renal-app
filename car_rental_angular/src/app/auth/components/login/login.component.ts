import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  error = '';
  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private authService:AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;
    if (this.loginForm.valid){
      this.authService.login(this.loginForm.value)
      .subscribe({
        
        next: (res) =>{
          if(res.userId != null){
            const user = {
              id:res.userId,
              role:"Customer"
            }
            StorageService.saveUser(user);
            StorageService.saveToken(res.jwt);
            StorageService.login();
            
            this.error = '';
            this.snackBar.open('Login successful!', 'Close', {
              duration: 3000,
              panelClass: ['success-snackbar']
            });
            setTimeout(() => this.router.navigateByUrl("/search"), 1000);
          }else if(res.error == "Invalid credentials"){
            this.error = 'Login failed'; 
            this.snackBar.open(this.error, 'Close', {
              duration: 3000,
              panelClass: ['error-snackbar']
            });
          }
        },

        error: (err) => {
          if (err.status === 429) {
            this.error = 'Too many login attempts. Please try again later.';
          } else if (err.status === 401 || err.error?.message === 'Invalid credentials') {
            this.error = 'Invalid username or password.';
          } else {
            this.error = 'An unexpected error occurred.';
          }

          this.snackBar.open(this.error, 'Close', {
            duration: 3000,
            panelClass: ['error-snackbar']
          });
        }

      })
    }
  }

}