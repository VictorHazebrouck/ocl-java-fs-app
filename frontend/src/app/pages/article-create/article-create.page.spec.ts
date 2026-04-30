import { TestBed, ComponentFixture } from "@angular/core/testing";
import { ArticleCreatePage } from "./article-create.page";
import { ArticleService } from "@app/services/article.service";
import { TopicService } from "@app/services/topic.service";
import { provideRouter, Router } from "@angular/router";
import { of } from "rxjs";
import { By } from "@angular/platform-browser";

describe("ArticleCreatePage", () => {
  const articleServiceMock = {
    createArticle: vi.fn(),
  };

  const topicServiceMock = {
    getSubscribedTopics: vi.fn(),
  };

  let fixture: ComponentFixture<ArticleCreatePage>;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticleCreatePage],
      providers: [
        provideRouter([]),
        { provide: ArticleService, useValue: articleServiceMock },
        { provide: TopicService, useValue: topicServiceMock },
      ],
    }).compileComponents();

    router = TestBed.inject(Router);
    vi.clearAllMocks();
  });

  it("should load topics on init", () => {
    topicServiceMock.getSubscribedTopics.mockReturnValue(
      of([
        { id: "1", name: "Angular" },
        { id: "2", name: "React" },
      ]),
    );

    fixture = TestBed.createComponent(ArticleCreatePage);
    fixture.detectChanges();

    expect(fixture.componentInstance.topics().length).toBe(2);
  });

  it("should create article and navigate home", async () => {
    topicServiceMock.getSubscribedTopics.mockReturnValue(of([{ id: "1", name: "Angular" }]));

    articleServiceMock.createArticle.mockReturnValue(of({}));

    const navigateSpy = vi.spyOn(router, "navigate");

    fixture = TestBed.createComponent(ArticleCreatePage);
    fixture.detectChanges();

    const component = fixture.componentInstance;

    component.topicNameSelected.set("Angular");
    component.title.set("My Article");
    component.content.set("Hello world");

    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css("app-button-component[variant='primary']"));

    button.triggerEventHandler("click", null);
    fixture.detectChanges();

    expect(articleServiceMock.createArticle).toHaveBeenCalledWith({
      title: "My Article",
      content: "Hello world",
      topicId: "1",
    });

    expect(navigateSpy).toHaveBeenCalledWith(["/"]);
  });

  it("should NOT create article if no topic selected", () => {
    topicServiceMock.getSubscribedTopics.mockReturnValue(of([{ id: "1", name: "Angular" }]));

    fixture = TestBed.createComponent(ArticleCreatePage);
    fixture.detectChanges();

    const component = fixture.componentInstance;

    component.title.set("Test");
    component.content.set("Content");

    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css("app-button-component[variant='primary']"));

    button.triggerEventHandler("click", null);
    fixture.detectChanges();

    expect(articleServiceMock.createArticle).not.toHaveBeenCalled();
  });
});
