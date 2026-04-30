import { TestBed } from "@angular/core/testing";
import { AuthService } from "./auth.service";
import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";

describe("AuthService", () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);

    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
  });

  const mockSession = {
    accessToken: "access-token",
    refreshToken: "refresh-token",
  };

  it("should sign up and store tokens", () => {
    const input = {
      username: "john",
      email: "john@test.com",
      password: "123456",
    };

    service.signUp(input).subscribe((session) => {
      expect(session).toEqual(mockSession);
      expect(localStorage.getItem("accessToken")).toBe("access-token");
      expect(localStorage.getItem("refreshToken")).toBe("refresh-token");
    });

    const req = httpMock.expectOne("/auth/signup");
    expect(req.request.method).toBe("POST");
    expect(req.request.body).toEqual(input);

    req.flush(mockSession);
  });

  it("should sign in and store tokens", () => {
    const input = {
      usernameOrEmail: "john",
      password: "123456",
    };

    service.signIn(input).subscribe(() => {
      expect(localStorage.getItem("accessToken")).toBe("access-token");
      expect(localStorage.getItem("refreshToken")).toBe("refresh-token");
    });

    const req = httpMock.expectOne("/auth/signin");
    expect(req.request.method).toBe("POST");

    req.flush(mockSession);
  });

  it("should clear tokens on sign out", () => {
    localStorage.setItem("accessToken", "x");
    localStorage.setItem("refreshToken", "y");

    service.signOut();

    expect(localStorage.getItem("accessToken")).toBeNull();
    expect(localStorage.getItem("refreshToken")).toBeNull();
  });

  it("should return true if signed in", () => {
    localStorage.setItem("accessToken", "x");
    expect(service.isSignedIn()).toBe(true);
  });

  it("should return false if not signed in", () => {
    expect(service.isSignedIn()).toBe(false);
  });

  it("should refresh token and store new session", () => {
    localStorage.setItem("refreshToken", "old-refresh");

    service.refresh().subscribe(() => {
      expect(localStorage.getItem("accessToken")).toBe("access-token");
      expect(localStorage.getItem("refreshToken")).toBe("refresh-token");
    });

    const req = httpMock.expectOne("/auth/refresh");
    expect(req.request.method).toBe("POST");
    expect(req.request.body).toEqual({ refreshToken: "old-refresh" });

    req.flush(mockSession);
  });

  it("should fetch profile", () => {
    const mockUser = {
      id: 1,
      username: "john",
      email: "john@test.com",
      emailVerified: "true",
      createdAt: "",
      updatedAt: "",
    };

    service.getProfile().subscribe((user) => {
      expect(user).toEqual(mockUser);
    });

    const req = httpMock.expectOne("/auth/user");
    expect(req.request.method).toBe("GET");

    req.flush(mockUser);
  });

  it("should update username", () => {
    service.setUsername("newName").subscribe();

    const req = httpMock.expectOne("/auth/username");
    expect(req.request.method).toBe("POST");
    expect(req.request.body).toEqual({ newUsername: "newName" });

    req.flush({});
  });

  it("should update password", () => {
    service.setPassword("newPass").subscribe();

    const req = httpMock.expectOne("/auth/password");
    expect(req.request.method).toBe("POST");
    expect(req.request.body).toEqual({ newPassword: "newPass" });

    req.flush({});
  });
});
