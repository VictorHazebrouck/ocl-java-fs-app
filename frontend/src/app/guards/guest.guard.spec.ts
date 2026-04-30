import { TestBed } from "@angular/core/testing";
import { Router, UrlTree, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { guestGuard } from "./guest.guard";
import { AuthService } from "../services/auth.service";

describe("guestGuard", () => {
  const mockRoute = {} as ActivatedRouteSnapshot;
  const mockState = {} as RouterStateSnapshot;

  let router: Router;

  const authServiceMock = {
    isSignedIn: vi.fn(),
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        {
          provide: Router,
          useValue: {
            createUrlTree: vi.fn(),
          },
        },
      ],
    });

    router = TestBed.inject(Router);
    vi.clearAllMocks();
  });

  it("should allow access when user is NOT signed in", () => {
    authServiceMock.isSignedIn.mockReturnValue(false);

    const result = TestBed.runInInjectionContext(() => guestGuard(mockRoute, mockState));

    expect(result).toBe(true);
  });

  it("should redirect to / when user IS signed in", () => {
    const fakeUrlTree = {} as UrlTree;

    authServiceMock.isSignedIn.mockReturnValue(true);
    // @ts-expect-error bla bla bla
    router.createUrlTree.mockReturnValue(fakeUrlTree);

    const result = TestBed.runInInjectionContext(() => guestGuard(mockRoute, mockState));

    expect(router.createUrlTree).toHaveBeenCalledWith(["/"]);
    expect(result).toBe(fakeUrlTree);
  });
});
