import { TestBed } from "@angular/core/testing";
import { ArticleService } from "./article.service";
import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";

describe("ArticleService", () => {
  let service: ArticleService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });

    service = TestBed.inject(ArticleService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("should fetch all articles", () => {
    const mockArticles = [
      {
        id: 1,
        title: "Test",
        content: "Content",
        createdAt: "",
        updatedAt: "",
        author: {
          id: 1,
          username: "john",
          email: "john@test.com",
          emailVerified: "true",
          createdAt: "",
          updatedAt: "",
        },
        topic: {
          id: 1,
          name: "Angular",
          description: "",
          createdAt: "",
          updatedAt: "",
        },
      },
    ];

    service.getArticles().subscribe((articles) => {
      expect(articles).toEqual(mockArticles);
    });

    const req = httpMock.expectOne("/api/article");
    expect(req.request.method).toBe("GET");

    req.flush(mockArticles);
  });

  it("should fetch an article by id", () => {
    const id = "123";

    const mockArticle = {
      id: 1,
      title: "Test",
      content: "Content",
      createdAt: "",
      updatedAt: "",
      author: {
        id: 1,
        username: "john",
        email: "john@test.com",
        emailVerified: "true",
        createdAt: "",
        updatedAt: "",
      },
      topic: {
        id: 1,
        name: "Angular",
        description: "",
        createdAt: "",
        updatedAt: "",
      },
    };

    service.getArticleById(id).subscribe((article) => {
      expect(article).toEqual(mockArticle);
    });

    const req = httpMock.expectOne(`/api/article/${id}`);
    expect(req.request.method).toBe("GET");

    req.flush(mockArticle);
  });

  it("should create an article", () => {
    const input = {
      title: "New article",
      content: "Some content",
      topicId: "1",
    };

    service.createArticle(input).subscribe((res) => {
      expect(res).toBeNull(); // because void
    });

    const req = httpMock.expectOne("api/article"); // 👈 note this
    expect(req.request.method).toBe("POST");
    expect(req.request.body).toEqual(input);

    req.flush(null);
  });
});
