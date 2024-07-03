import { inject } from "@angular/core";
import { CanActivateFn, Router, ActivatedRouteSnapshot } from "@angular/router";

export const authGuard: (protectedRoutes: string[]) => CanActivateFn = (protectedRoutes: string[]) => {
  return (route: ActivatedRouteSnapshot) => {
    const router = inject(Router);

    //Bisogna usare ActivatedRouteSnapshot per ottenere l'url successiva.

    /* Il guard viene attivato ancora prima di entrare nella componente, quindi se chiedessi l'url al router, mi
    restituirebbe l'url della componente precedente */

    const currentRoute = "/" + route.url.map(segment => segment.path).join();
    const authenticated = isAuthenticated();

    if ((!authenticated) && protectedRoutes.includes(currentRoute) && typeof window !== "undefined") {
      router.navigate(['login']).then();
      return false;
    }
    else if (authenticated && currentRoute == '/login' || authenticated &&currentRoute == '/register') {
      router.navigate(['']).then();
      return false;
    }
    return true;
  };

  function isAuthenticated() : boolean {
    let token = null;
    if (typeof window !== "undefined") //scarto finestre che non siano undefined
      token = localStorage.getItem('token');
    return !!(token);
  }
};

const protectedRoutes: string[] = ['/myprofile', '/nuovaricetta', '/myrecipes'];
export const canActivate = authGuard(protectedRoutes);
