import { TestBed } from "@angular/core/testing";
import { HttpClient, provideHttpClient, withInterceptors } from "@angular/common/http";
import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";

import { authInterceptor } from "./auth.interceptor";
import { AuthService } from "../services/auth.service";
import { of, throwError } from "rxjs";

describe("authInterceptor", () => {
  let httpMock: HttpTestingController;
  let http: HttpClient;

  const authServiceMock = {
    getAccessToken: vitest.fn(),
    refresh: vitest.fn(),
    signOut: vitest.fn(),
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([authInterceptor])),
        provideHttpClientTesting(),
        { provide: AuthService, useValue: authServiceMock },
      ],
    });

    http = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);

    vitest.clearAllMocks();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("should add Authorization header when token exists", () => {
    authServiceMock.getAccessToken.mockReturnValue("abc-token");

    http.get("/test").subscribe();

    const req = httpMock.expectOne("/test");

    expect(req.request.headers.get("Authorization")).toBe("Bearer abc-token");

    req.flush({});
  });

  it("should NOT add Authorization header when no token", () => {
    authServiceMock.getAccessToken.mockReturnValue(null);

    http.get("/test").subscribe();

    const req = httpMock.expectOne("/test");

    expect(req.request.headers.has("Authorization")).toBe(false);

    req.flush({});
  });

  it("should refresh token on 401 and retry request", () => {
    authServiceMock.getAccessToken
      .mockReturnValueOnce("old-token") // first request
      .mockReturnValueOnce("new-token"); // retry request

    authServiceMock.refresh.mockReturnValue(
      of({
        accessToken: "new-token",
        refreshToken: "new-refresh",
      }),
    );

    http.get("/test").subscribe();

    // 1st request
    const firstReq = httpMock.expectOne("/test");
    expect(firstReq.request.headers.get("Authorization")).toBe("Bearer old-token");

    // trigger 401
    firstReq.flush({}, { status: 401, statusText: "Unauthorized" });

    // retry request
    const retriedReq = httpMock.expectOne("/test");

    expect(retriedReq.request.headers.get("Authorization")).toBe("Bearer new-token");

    retriedReq.flush({});
  });

  it("should rethrow non-401 errors", () => {
    authServiceMock.getAccessToken.mockReturnValue("token");

    http.get("/test").subscribe({
      error: (err) => {
        expect(err.status).toBe(500);
      },
    });

    const req = httpMock.expectOne("/test");

    req.flush("Server error", {
      status: 500,
      statusText: "Server Error",
    });
  });

  it("should logout if refresh fails", () => {
    authServiceMock.getAccessToken.mockReturnValue("old-token");

    authServiceMock.refresh.mockReturnValue(throwError(() => new Error("refresh failed")));

    http.get("/test").subscribe({
      error: () => {
        expect(authServiceMock.signOut).toHaveBeenCalled();
      },
    });

    const req = httpMock.expectOne("/test");

    req.flush({}, { status: 401, statusText: "Unauthorized" });
  });
});
