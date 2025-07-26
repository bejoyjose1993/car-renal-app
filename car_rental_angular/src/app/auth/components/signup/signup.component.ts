import { Component } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  signupForm: FormGroup;
  error = '';
  constructor(private fb: FormBuilder, 
              private authService:AuthService, 
              private snackBar: MatSnackBar,
              private router: Router) {
    this.signupForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(form: FormGroup) {
    return form.get('password')?.value === form.get('confirmPassword')?.value
      ? null : { passwordMismatch: true };
  }

  onSubmit() {
    if (this.signupForm.valid) {
      this.authService.register(this.signupForm.value)
        .subscribe({
        
        next: (response) => {
          if(response.id != null){
            this.snackBar.open('Signup successful!', 'Close', {
              duration: 3000, // milliseconds
              panelClass: ['success-snackbar']
            });

            setTimeout(() => {
              this.router.navigateByUrl("/login");
            }, 1000);
          }else {
            this.snackBar.open('Signup failed. Please try again.', 'Close', {
              duration: 3000,
              panelClass: ['error-snackbar']
            });
          }
        },  

        error: (err) => {
          if (err.status === 429) {
            this.error = 'Too many Signup attempts. Please try again later.';
          } else {
            this.error = 'An unexpected error occurred.';
          }

          this.snackBar.open(this.error, 'Close', {
            duration: 3000,
            panelClass: ['error-snackbar']
          });
        }
      })
      // Handle sign-up logic here
    }
  }
}
