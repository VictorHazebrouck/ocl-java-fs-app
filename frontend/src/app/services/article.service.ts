import { HttpClient } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable } from "rxjs";
import { ArticleCreation, ArticleWithAuthorAndTopic } from "../models/article.model";

export class ArticleService {
  private http = inject(HttpClient);

  public articleGet(): Observable<ArticleWithAuthorAndTopic[]> {
    return this.http.get<ArticleWithAuthorAndTopic[]>("/api/article");
  }

  public articleGetById(id: string): Observable<ArticleWithAuthorAndTopic> {
    return this.http.get<ArticleWithAuthorAndTopic>(`/api/article/${id}`);
  }

  public articleCreate(input: ArticleCreation): Observable<void> {
    return this.http.post<void>("api/article", input);
  }
}
