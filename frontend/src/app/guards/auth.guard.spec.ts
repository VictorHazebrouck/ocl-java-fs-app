import { TestBed } from "@angular/core/testing";
import { Router, UrlTree } from "@angular/router";
import { authGuard } from "./auth.guard";
import { AuthService } from "../services/auth.service";
import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";

describe("authGuard", () => {
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

  it("should allow access when user is signed in", () => {
    authServiceMock.isSignedIn.mockReturnValue(true);

    const result = TestBed.runInInjectionContext(() => authGuard(mockRoute, mockState));

    expect(result).toBe(true);
  });

  it("should redirect to /sign when user is NOT signed in", () => {
    const fakeUrlTree = {} as UrlTree;

    authServiceMock.isSignedIn.mockReturnValue(false);
    // @ts-expect-error bla bla
    router.createUrlTree.mockReturnValue(fakeUrlTree);

    const result = TestBed.runInInjectionContext(() => authGuard(mockRoute, mockState));

    expect(router.createUrlTree).toHaveBeenCalledWith(["/sign"]);
    expect(result).toBe(fakeUrlTree);
  });
});
