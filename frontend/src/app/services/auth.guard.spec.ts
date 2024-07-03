import { TestBed } from '@angular/core/testing';
import { CanActivateFn, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { authGuard } from './auth.guard';

describe('authGuard', () => {
  let executeGuard: CanActivateFn;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: Router, useValue: { navigate: jasmine.createSpy('navigate') } }
      ]
    });
    router = TestBed.inject(Router);
    executeGuard = authGuard(['/profile']); // Passare qui i percorsi protetti necessari per il test
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });

  it('should redirect to login if not authenticated', () => {
    spyOn(localStorage, 'getItem').and.returnValue(null); // Simulazione di mancanza di token
    const canActivate = executeGuard({ url: [{ path: 'profile' }] } as ActivatedRouteSnapshot, {} as RouterStateSnapshot);
    expect(canActivate).toBe(false);
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should allow access if authenticated', () => {
    spyOn(localStorage, 'getItem').and.returnValue('fake-token'); // Simulazione di presenza di un token
    const canActivate = executeGuard({ url: [{ path: 'profile' }] } as ActivatedRouteSnapshot, {} as RouterStateSnapshot);
    expect(canActivate).toBe(true);
    expect(router.navigate).not.toHaveBeenCalled();
  });
});
