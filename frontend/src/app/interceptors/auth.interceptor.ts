import { inject } from "@angular/core/primitives/di";
import { catchError, switchMap, throwError } from "rxjs";
import { AuthService } from "../services/auth.service";
import { HttpInterceptorFn } from "@angular/common/http";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getAccessToken();

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  return next(req).pipe(
    catchError((err) => {
      if (err.status !== 401) {
        return throwError(() => err);
      }

      return authService.refresh().pipe(
        switchMap(() => {
          const newToken = authService.getAccessToken();

          if (!newToken) {
            authService.signOut();
            return throwError(() => err);
          }

          const newReq = req.clone({
            setHeaders: {
              Authorization: `Bearer ${newToken}`,
            },
          });

          return next(newReq);
        }),
        catchError((refreshError) => {
          authService.signOut();
          return throwError(() => refreshError);
        }),
      );

      // return throwError(() => err);
    }),
  );
};
