import { TestBed, ComponentFixture } from "@angular/core/testing";
import { HomePage } from "./home.page";
import { ArticleService } from "@app/services/article.service";
import { of } from "rxjs";
import { By } from "@angular/platform-browser";
import { provideRouter } from "@angular/router";

describe("HomePage", () => {
  const articleServiceMock = {
    getArticles: vi.fn(),
  };

  let fixture: ComponentFixture<HomePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomePage],
      providers: [provideRouter([]), { provide: ArticleService, useValue: articleServiceMock }],
    }).compileComponents();

    vi.clearAllMocks();
  });

  it("should load articles on init", () => {
    articleServiceMock.getArticles.mockReturnValue(
      of([
        { id: "1", createdAt: "2023-01-01" },
        { id: "2", createdAt: "2024-01-01" },
      ]),
    );

    fixture = TestBed.createComponent(HomePage);
    fixture.detectChanges();

    const cards = fixture.debugElement.queryAll(By.css("app-article-card-component"));

    expect(cards.length).toBe(2);
  });
});
