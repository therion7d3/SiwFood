import {HttpErrorResponse, HttpHandlerFn, HttpInterceptorFn, HttpRequest} from "@angular/common/http";
import {catchError, tap, throwError} from "rxjs";
import { Router } from '@angular/router';
import {inject} from "@angular/core";

export const outgoingTokenInterceptorInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next:
  HttpHandlerFn) => {
  const router = inject(Router);
  const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
  if (token !== null) {
    const modifiedReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`),
    });
    return next(modifiedReq).pipe(
      catchError((err: any) => {
        if (err instanceof HttpErrorResponse) {
          // Handle HTTP errors
          if (err.status === 401) {
            console.error('Unauthorized request:', err);
            localStorage.removeItem('token');
            localStorage.removeItem('email');
            localStorage.removeItem('userId');
            localStorage.removeItem('username ');
            localStorage.removeItem('role ');
            router.navigate(['login']).then();
          } else {
            console.error('HTTP error:', err);
          }
        } else {
          console.error('An error occurred:', err);
        }
        return throwError(() => err);
      })
    )
  }
  else return next(req);
}
