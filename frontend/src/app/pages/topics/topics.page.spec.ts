import { TestBed, ComponentFixture } from "@angular/core/testing";
import { TopicsPage } from "./topics.page";
import { TopicService } from "@app/services/topic.service";
import { of } from "rxjs";
import { By } from "@angular/platform-browser";
import { provideRouter } from "@angular/router";

describe("TopicsPage (real DOM)", () => {
  const topicServiceMock = {
    getTopicsWithSubscriptionInfo: vi.fn(),
    createTopicSubscription: vi.fn(),
    deleteTopicSubscription: vi.fn(),
  };

  let fixture: ComponentFixture<TopicsPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopicsPage],
      providers: [provideRouter([]), { provide: TopicService, useValue: topicServiceMock }],
    }).compileComponents();

    vi.clearAllMocks();
  });

  it("should load topics on init", () => {
    topicServiceMock.getTopicsWithSubscriptionInfo.mockReturnValue(
      of([{ id: "1", name: "Angular", amISubscribed: false }]),
    );

    fixture = TestBed.createComponent(TopicsPage);
    fixture.detectChanges();

    expect(topicServiceMock.getTopicsWithSubscriptionInfo).toHaveBeenCalled();
  });

  it("should call subscribe when clicking subscribe button", () => {
    topicServiceMock.getTopicsWithSubscriptionInfo.mockReturnValue(
      of([{ id: "1", name: "Angular", amISubscribed: false }]),
    );

    topicServiceMock.createTopicSubscription.mockReturnValue(of(void 0));

    fixture = TestBed.createComponent(TopicsPage);
    fixture.detectChanges();

    // Find ThemeCardComponent in DOM
    const cards = fixture.debugElement.queryAll(By.css("app-theme-card-component"));

    expect(cards.length).toBe(1);

    // Get component instance
    const cardInstance = cards[0].componentInstance;

    // Trigger output (real Angular event flow)
    cardInstance.onClickSubscribe.emit();

    expect(topicServiceMock.createTopicSubscription).toHaveBeenCalledWith("1");
  });

  it("should call unsubscribe when clicking unsubscribe button", () => {
    topicServiceMock.getTopicsWithSubscriptionInfo.mockReturnValue(
      of([{ id: "1", name: "Angular", amISubscribed: true }]),
    );

    topicServiceMock.deleteTopicSubscription.mockReturnValue(of(void 0));

    fixture = TestBed.createComponent(TopicsPage);
    fixture.detectChanges();

    const cards = fixture.debugElement.queryAll(By.css("app-theme-card-component"));

    const cardInstance = cards[0].componentInstance;

    cardInstance.onClickUnubscribe.emit();

    expect(topicServiceMock.deleteTopicSubscription).toHaveBeenCalledWith("1");
  });
});
