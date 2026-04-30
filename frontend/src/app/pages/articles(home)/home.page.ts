import { Component, computed, inject, signal } from "@angular/core";
import { ThreePartsLayout } from "@app/components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "@app/components/ui/button/button.component";
import { ArticleCardComponent } from "@app/components/article-card/article-card.component";
import { GridLayout } from "@app/components/layouts/grid-layout/grid.layout";
import { RouterLink } from "@angular/router";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowDown, lucideArrowUp } from "@ng-icons/lucide";
import { ArticleService } from "@app/services/article.service";
import { toSignal } from "@angular/core/rxjs-interop";

@Component({
  selector: "app-home-page",
  imports: [
    HeaderFullComponent,
    ThreePartsLayout,
    ButtonComponent,
    ArticleCardComponent,
    GridLayout,
    RouterLink,
    ScrollComponent,
    NgIcon,
  ],
  viewProviders: [provideIcons({ lucideArrowDown, lucideArrowUp })],
  templateUrl: "./home.page.html",
  host: { style: "display: contents;" },
})
export class HomePage {
  private readonly articleService = inject(ArticleService);

  readonly sortBy = signal<"asc" | "desc">("asc");
  readonly articles = toSignal(this.articleService.getArticles(), { initialValue: [] });
  readonly articlesSorted = computed(() =>
    this.articles().sort((a, b) => {
      if (this.sortBy() == "asc") {
        return new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime();
      } else {
        return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
      }
    }),
  );

  protected toggleSortBy() {
    this.sortBy.update((value) => (value === "asc" ? "desc" : "asc"));
  }
}
