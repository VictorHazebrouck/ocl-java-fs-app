import { TestBed, ComponentFixture } from "@angular/core/testing";
import { SignupPage } from "./signup.page";
import { AuthService } from "@app/services/auth.service";
import { provideRouter, Router } from "@angular/router";
import { of } from "rxjs";
import { By } from "@angular/platform-browser";

describe("SignupPage", () => {
  const authServiceMock = {
    signUp: vi.fn(),
  };

  let fixture: ComponentFixture<SignupPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignupPage],
      providers: [provideRouter([]), { provide: AuthService, useValue: authServiceMock }],
    }).compileComponents();

    vi.clearAllMocks();
  });

  it("should signup and navigate to home", () => {
    authServiceMock.signUp.mockReturnValue(of({}));

    const router = TestBed.inject(Router);
    const navigateSpy = vi.spyOn(router, "navigate");

    fixture = TestBed.createComponent(SignupPage);
    fixture.detectChanges();

    const inputs = fixture.debugElement.queryAll(By.css("app-text-input-with-label-component"));

    inputs[0].componentInstance.value.set("john");
    inputs[1].componentInstance.value.set("john@mail.com");
    inputs[2].componentInstance.value.set("123456");

    const button = fixture.debugElement.query(By.css("app-button-component[variant='primary']"));

    button.triggerEventHandler("click", null);

    expect(authServiceMock.signUp).toHaveBeenCalledWith({
      username: "john",
      email: "john@mail.com",
      password: "123456",
    });

    expect(navigateSpy).toHaveBeenCalledWith(["/"]);
  });
});
