import { Component, inject, model, signal } from "@angular/core";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowLeft, lucideSend } from "@ng-icons/lucide";
import { CommentCardComponent } from "@app/components/comment-card/comment-card.component";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { TextareaComponent } from "@app/components/ui/textarea/textarea.component";
import { ActivatedRoute, RouterLink } from "@angular/router";
import { ArticleService } from "@app/services/article.service";
import { toSignal } from "@angular/core/rxjs-interop";
import { DatePipe } from "@angular/common";
import { CommentService } from "@app/services/comment.service";
import { map, switchMap, take } from "rxjs";
import { CommentWithAuthor } from "@app/models/comment.model";

@Component({
  selector: "app-article-by-id-page",
  imports: [
    HeaderFullComponent,
    ScrollComponent,
    NgIcon,
    DatePipe,
    CommentCardComponent,
    TextareaComponent,
    RouterLink,
  ],
  viewProviders: [provideIcons({ lucideArrowLeft, lucideSend })],
  templateUrl: "./article-by-id.page.html",
  host: { style: "display: contents;" },
})
export class ArticleByIdPage {
  private readonly route = inject(ActivatedRoute);
  private readonly articleService = inject(ArticleService);
  private readonly commentSerice = inject(CommentService);

  article = toSignal(
    this.route.paramMap.pipe(
      map((params) => params.get("id")!),
      switchMap((id) => this.articleService.getArticleById(id)),
    ),
  );

  comments = signal<CommentWithAuthor[]>([]);

  commentContent = model("");

  constructor() {
    this.refreshComments();
  }

  addComment() {
    const article = this.article();
    if (!article?.id) return;
    this.commentSerice
      .createComment({
        articleId: article.id,
        content: this.commentContent(),
      })
      .pipe(take(1))
      .subscribe({
        next: () => this.refreshComments(),
        error: console.error,
      });
  }

  private refreshComments() {
    if (this.article()?.id) {
      this.commentSerice
        .getComments(this.article()!.id)
        .pipe(take(1))
        .subscribe({
          next: (comments) => this.comments.set(comments),
          error: console.error,
        });
    }
  }
}
