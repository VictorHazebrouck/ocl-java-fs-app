import { TestBed, ComponentFixture } from "@angular/core/testing";
import { ProfilePage } from "./profile.page";
import { TopicService } from "@app/services/topic.service";
import { AuthService } from "@app/services/auth.service";
import { of } from "rxjs";
import { By } from "@angular/platform-browser";
import { provideRouter } from "@angular/router";

describe("ProfilePage", () => {
  const topicServiceMock = {
    getSubscribedTopics: vi.fn(),
    deleteTopicSubscription: vi.fn(),
  };

  const authServiceMock = {
    getProfile: vi.fn(),
    setUsername: vi.fn(),
    setPassword: vi.fn(),
  };

  let fixture: ComponentFixture<ProfilePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfilePage],
      providers: [
        provideRouter([]),
        { provide: TopicService, useValue: topicServiceMock },
        { provide: AuthService, useValue: authServiceMock },
      ],
    }).compileComponents();

    vi.clearAllMocks();
  });

  it("should load profile + topics on init", () => {
    topicServiceMock.getSubscribedTopics.mockReturnValue(of([]));
    authServiceMock.getProfile.mockReturnValue(of({ username: "john", email: "john@mail.com" }));

    fixture = TestBed.createComponent(ProfilePage);
    fixture.detectChanges();

    expect(authServiceMock.getProfile).toHaveBeenCalled();
    expect(topicServiceMock.getSubscribedTopics).toHaveBeenCalled();
  });

  it("should update username and password on save click", () => {
    topicServiceMock.getSubscribedTopics.mockReturnValue(of([]));
    authServiceMock.getProfile.mockReturnValue(of({ username: "john", email: "john@mail.com" }));

    authServiceMock.setUsername.mockReturnValue(of({}));
    authServiceMock.setPassword.mockReturnValue(of({}));

    fixture = TestBed.createComponent(ProfilePage);
    fixture.detectChanges();

    // ---- INPUTS ----
    const inputs = fixture.debugElement.queryAll(By.css("app-text-input-component"));

    inputs[0].componentInstance.value.set("newUser");
    inputs[1].componentInstance.value.set("new@mail.com");
    inputs[2].componentInstance.value.set("newPassword");

    // ---- CLICK SAVE ----
    const button = fixture.debugElement.query(By.css("app-button-component[variant='primary']"));

    button.triggerEventHandler("click", null);

    // ---- ASSERT ----
    expect(authServiceMock.setUsername).toHaveBeenCalledWith("newUser");
    expect(authServiceMock.setPassword).toHaveBeenCalledWith("newPassword");
  });

  it("should unsubscribe from topic", () => {
    topicServiceMock.getSubscribedTopics.mockReturnValue(of([{ id: "1", name: "Angular" }]));

    authServiceMock.getProfile.mockReturnValue(of({ username: "john", email: "john@mail.com" }));

    topicServiceMock.deleteTopicSubscription.mockReturnValue(of({}));

    fixture = TestBed.createComponent(ProfilePage);
    fixture.detectChanges();

    const card = fixture.debugElement.query(By.css("app-theme-card-component"));

    card.componentInstance.onClickUnubscribe.emit();

    expect(topicServiceMock.deleteTopicSubscription).toHaveBeenCalledWith("1");
  });
});
