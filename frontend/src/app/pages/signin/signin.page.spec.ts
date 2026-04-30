import { TestBed, ComponentFixture } from "@angular/core/testing";
import { SigninPage } from "./signin.page";
import { AuthService } from "@app/services/auth.service";
import { provideRouter, Router } from "@angular/router";
import { of } from "rxjs";
import { By } from "@angular/platform-browser";

describe("SigninPage", () => {
  const authServiceMock = {
    signIn: vi.fn(),
  };

  let fixture: ComponentFixture<SigninPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SigninPage],
      providers: [provideRouter([]), { provide: AuthService, useValue: authServiceMock }],
    }).compileComponents();

    vi.clearAllMocks();
  });

  it("should sign in and navigate to home", () => {
    authServiceMock.signIn.mockReturnValue(of({}));

    const router = TestBed.inject(Router);
    const navigateSpy = vi.spyOn(router, "navigate");

    fixture = TestBed.createComponent(SigninPage);
    fixture.detectChanges();

    // ---- INPUTS ----
    const inputs = fixture.debugElement.queryAll(By.css("app-text-input-with-label-component"));

    inputs[0].componentInstance.value.set("john@mail.com");
    inputs[1].componentInstance.value.set("123456");

    // ---- CLICK ----
    const button = fixture.debugElement.query(By.css("app-button-component[variant='primary']"));

    button.triggerEventHandler("click", null);

    // ---- ASSERT ----
    expect(authServiceMock.signIn).toHaveBeenCalledWith({
      usernameOrEmail: "john@mail.com",
      password: "123456",
    });

    expect(navigateSpy).toHaveBeenCalledWith(["/"]);
  });
});
