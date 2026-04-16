import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ArticleCreation, ArticleWithAuthorAndTopic } from "../models/article.model";

@Injectable({
  providedIn: "root",
})
export class ArticleService {
  private http = inject(HttpClient);

  public getArticle(): Observable<ArticleWithAuthorAndTopic[]> {
    return this.http.get<ArticleWithAuthorAndTopic[]>("/api/article");
  }

  public getArticleById(id: string): Observable<ArticleWithAuthorAndTopic> {
    return this.http.get<ArticleWithAuthorAndTopic>(`/api/article/${id}`);
  }

  public createArticle(input: ArticleCreation): Observable<void> {
    return this.http.post<void>("api/article", input);
  }
}
