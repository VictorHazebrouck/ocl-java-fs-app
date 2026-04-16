import { Component, computed, inject, model, signal } from "@angular/core";
import { toSignal } from "@angular/core/rxjs-interop";
import { Router, RouterLink } from "@angular/router";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { ThreePartsLayout } from "@app/components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "@app/components/ui/button/button.component";
import { DropdownComponent } from "@app/components/ui/dropdown/dropdown.component";
import { TextInputComponent } from "@app/components/ui/text-input/text-input.component";
import { TextareaComponent } from "@app/components/ui/textarea/textarea.component";
import { ArticleService } from "@app/services/article.service";
import { TopicService } from "@app/services/topic.service";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowLeft } from "@ng-icons/lucide";

@Component({
  selector: "app-article-create-page",
  imports: [
    HeaderFullComponent,
    ThreePartsLayout,
    DropdownComponent,
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
  private readonly topicService = inject(TopicService);
  private readonly router = inject(Router);

  protected topics = toSignal(this.topicService.getTopics(), { initialValue: [] });
  protected topicNameSelected = signal("");
  protected topicId = computed(() => {
    return this.topics().find((e) => e.name === this.topicNameSelected())?.id;
  });

  protected title = model("");
  protected content = model("");

  protected createArticle() {
    const topicId = this.topicId();
    if (!topicId) {
      return;
    }

    this.articleService
      .createArticle({
        title: this.title(),
        content: this.content(),
        topicId: topicId,
      })
      .subscribe({
        next: () => this.router.navigate(["/"]),
        error: console.error,
      });
  }
}
