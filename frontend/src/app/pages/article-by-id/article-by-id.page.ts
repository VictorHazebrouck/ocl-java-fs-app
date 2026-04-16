import { Component, inject, input } from "@angular/core";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowLeft, lucideSend } from "@ng-icons/lucide";
import { CommentCardComponent } from "@app/components/comment-card/comment-card.component";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { TextareaComponent } from "@app/components/ui/textarea/textarea.component";
import { RouterLink } from "@angular/router";
import { ArticleService } from "@app/services/article.service";
import { toSignal } from "@angular/core/rxjs-interop";
import { DatePipe } from "@angular/common";

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
  private readonly articleService = inject(ArticleService);

  article = toSignal(this.articleService.getArticleById("1"));
}
