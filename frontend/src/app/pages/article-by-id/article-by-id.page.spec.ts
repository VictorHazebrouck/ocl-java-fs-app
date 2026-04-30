import { TestBed, ComponentFixture } from "@angular/core/testing";
import { ArticleByIdPage } from "./article-by-id.page";
import { ArticleService } from "@app/services/article.service";
import { CommentService } from "@app/services/comment.service";
import { provideRouter } from "@angular/router";
import { of } from "rxjs";
import { By } from "@angular/platform-browser";

describe("ArticleByIdPage", () => {
  const articleServiceMock = {
    getArticleById: vi.fn(),
  };

  const commentServiceMock = {
    getComments: vi.fn(),
    createComment: vi.fn(),
  };

  let fixture: ComponentFixture<ArticleByIdPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticleByIdPage],
      providers: [
        provideRouter([
          {
            path: "article/:id",
            component: ArticleByIdPage,
          },
        ]),
        { provide: ArticleService, useValue: articleServiceMock },
        { provide: CommentService, useValue: commentServiceMock },
      ],
    }).compileComponents();

    vi.clearAllMocks();
  });

  it("should load article and comments on init", () => {
    articleServiceMock.getArticleById.mockReturnValue(
      of({
        id: "1",
        title: "Hello",
        content: "World",
        createdAt: "2024-01-01",
        author: { username: "john" },
        topic: { name: "Angular" },
      }),
    );

    commentServiceMock.getComments.mockReturnValue(
      of([
        {
          id: "c1",
          content: "Nice post",
          author: { username: "alice" },
        },
      ]),
    );

    fixture = TestBed.createComponent(ArticleByIdPage);
    fixture.detectChanges();

    const component = fixture.componentInstance;

    expect(component.article()).toBeTruthy();
    expect(component.comments().length).toBe(1);
  });

  it("should render article title in DOM", () => {
    articleServiceMock.getArticleById.mockReturnValue(
      of({
        id: "1",
        title: "My Article",
        content: "Hello",
        createdAt: "2024-01-01",
        author: { username: "john" },
        topic: { name: "Angular" },
      }),
    );

    commentServiceMock.getComments.mockReturnValue(of([]));

    fixture = TestBed.createComponent(ArticleByIdPage);
    fixture.detectChanges();

    const title = fixture.debugElement.query(By.css("h3"));

    expect(title.nativeElement.textContent).toContain("My Article");
  });

  it("should add comment and refresh list", async () => {
    articleServiceMock.getArticleById.mockReturnValue(
      of({
        id: "1",
        title: "My Article",
        content: "Hello",
        createdAt: "2024-01-01",
        author: { username: "john" },
        topic: { name: "Angular" },
      }),
    );

    commentServiceMock.getComments.mockReturnValueOnce(of([])).mockReturnValueOnce(
      of([
        {
          id: "c1",
          content: "New comment",
          author: { username: "alice" },
        },
      ]),
    );

    commentServiceMock.createComment.mockReturnValue(of({}));

    fixture = TestBed.createComponent(ArticleByIdPage);
    fixture.detectChanges();

    await fixture.whenStable(); // 👈 IMPORTANT
    fixture.detectChanges();

    const component = fixture.componentInstance;

    component.commentContent.set("Hello comment");

    const button = fixture.debugElement.query(By.css("button.cursor-pointer"));

    button.triggerEventHandler("click", null);

    fixture.detectChanges();

    expect(commentServiceMock.getComments).toHaveBeenCalled();
  });

  it("should NOT add comment if article is missing", () => {
    articleServiceMock.getArticleById.mockReturnValue(of(null));

    fixture = TestBed.createComponent(ArticleByIdPage);
    fixture.detectChanges();

    const component = fixture.componentInstance;

    component.commentContent.set("test");

    expect(() => component.addComment()).not.toThrow();
    expect(commentServiceMock.createComment).not.toHaveBeenCalled();
  });
});
