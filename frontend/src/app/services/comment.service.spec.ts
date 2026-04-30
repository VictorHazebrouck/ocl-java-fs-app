import { TestBed } from "@angular/core/testing";
import { CommentService } from "./comment.service";
import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";

describe("CommentService", () => {
  let service: CommentService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });

    service = TestBed.inject(CommentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("should fetch comments for an article", () => {
    const articleId = "123";

    const mockResponse = [
      {
        id: 1,
        article: 1,
        content: "Nice post!",
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
      },
    ];

    service.getComments(articleId).subscribe((comments) => {
      expect(comments).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`/api/comment/${articleId}`);
    expect(req.request.method).toBe("GET");

    req.flush(mockResponse);
  });

  it("should create a comment", () => {
    const body = {
      articleId: "123",
      content: "Great article!",
    };

    const mockResponse = {
      id: 1,
      article: 1,
      author: 1,
      content: "Great article!",
      createdAt: "",
      updatedAt: "",
    };

    service.createComment(body).subscribe((comment) => {
      expect(comment).toEqual(mockResponse);
    });

    const req = httpMock.expectOne("/api/comment");
    expect(req.request.method).toBe("POST");
    expect(req.request.body).toEqual(body);

    req.flush(mockResponse);
  });
});
