import { TestBed } from "@angular/core/testing";
import { TopicService } from "./topic.service";
import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";

describe("TopicService", () => {
  let service: TopicService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });

    service = TestBed.inject(TopicService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // ensure no unmatched requests
  });

  it("should fetch topics", () => {
    const mockTopics = [
      { id: 1, name: "Angular", description: "desc", createdAt: "", updatedAt: "" },
    ];

    service.getTopics().subscribe((topics) => {
      expect(topics).toEqual(mockTopics);
    });

    const req = httpMock.expectOne("/api/topic");
    expect(req.request.method).toBe("GET");
    req.flush(mockTopics);
  });

  it("should fetch topics with subscription info", () => {
    service.getTopicsWithSubscriptionInfo().subscribe();

    const req = httpMock.expectOne("/api/topic/with-am-i-subscribed");
    expect(req.request.method).toBe("GET");
    req.flush([]);
  });

  it("should fetch subscribed topics", () => {
    service.getSubscribedTopics().subscribe();

    const req = httpMock.expectOne("/api/topic/only-subscribed");
    expect(req.request.method).toBe("GET");
    req.flush([]);
  });

  it("should create a topic subscription", () => {
    const topicId = "123";

    service.createTopicSubscription(topicId).subscribe();

    const req = httpMock.expectOne(`/api/subscription/${topicId}`);
    expect(req.request.method).toBe("POST");
    expect(req.request.body).toEqual({});
    req.flush(null);
  });

  it("should delete a topic subscription", () => {
    const topicId = "123";

    service.deleteTopicSubscription(topicId).subscribe();

    const req = httpMock.expectOne(`/api/subscription/${topicId}`);
    expect(req.request.method).toBe("DELETE");
    req.flush(null);
  });
});
