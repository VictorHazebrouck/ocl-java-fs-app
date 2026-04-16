import { Component, inject, model } from "@angular/core";
import { Router, RouterLink } from "@angular/router";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { ThreePartsLayout } from "@app/components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "@app/components/ui/button/button.component";
import { TextInputComponent } from "@app/components/ui/text-input/text-input.component";
import { TextareaComponent } from "@app/components/ui/textarea/textarea.component";
import { ArticleService } from "@app/services/article.service";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowLeft } from "@ng-icons/lucide";

@Component({
  selector: "app-article-create-page",
  imports: [
    HeaderFullComponent,
    ThreePartsLayout,
    ButtonComponent,
    TextInputComponent,
    RouterLink,
    TextareaComponent,
    NgIcon,
  ],
  viewProviders: [provideIcons({ lucideArrowLeft })],
  templateUrl: "./article-create.page.html",
  host: { style: "display: contents;" },
})
export class ArticleCreatePage {
  private readonly articleService = inject(ArticleService);
  private readonly router = inject(Router);

  protected title = model("");
  protected content = model("");
  protected topicId = model("");

  protected createArticle() {
    this.articleService
      .createArticle({
        title: this.title(),
        content: this.content(),
        topicId: this.topicId(),
      })
      .subscribe({
        next: () => this.router.navigate(["/"]),
        error: console.error,
      });
  }
}
