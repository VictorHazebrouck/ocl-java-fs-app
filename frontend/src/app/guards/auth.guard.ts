import { CanActivateFn, Router } from "@angular/router";
import { inject } from "@angular/core";
import { AuthService } from "../services/auth.service";

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const isLoggedIn = !!authService.getAccessToken();

  if (!isLoggedIn) {
    return router.createUrlTree(["/signin"]); // 🔁 redirect if NOT logged in
  }

  return true;
};
