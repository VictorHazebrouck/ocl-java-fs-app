import { Component, signal } from "@angular/core";
import { ThreePartsLayout } from "@app/components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "@app/components/ui/button/button.component";
import { ArticleCardComponent } from "@app/components/article-card/article-card.component";
import { GridLayout } from "@app/components/layouts/grid-layout/grid.layout";
import { RouterLink } from "@angular/router";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowDown, lucideArrowUp } from "@ng-icons/lucide";

@Component({
  selector: "home-page",
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
  protected readonly sortBy = signal<"asc" | "desc">("asc");

  protected toggleSortBy() {
    this.sortBy.update((value) => (value === "asc" ? "desc" : "asc"));
  }
}
