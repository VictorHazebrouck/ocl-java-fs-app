import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Comment, CommentWithAuthor } from "@app/models/comment.model";

@Injectable({
  providedIn: "root",
})
export class CommentService {
  private http = inject(HttpClient);

  public getComments(articleId: string): Observable<CommentWithAuthor[]> {
    return this.http.get<CommentWithAuthor[]>(`/api/comment/${articleId}`);
  }

  public createComment(body: { articleId: string; content: string }): Observable<Comment> {
    console.log("KSNDOIWNCUNICW: ", body);

    return this.http.post<Comment>("/api/comment", body);
  }
}
