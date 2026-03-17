import { Component, signal } from "@angular/core";
import { ThreePartsLayout } from "../../components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "../../components/ui/button/button.component";
import { ArticleCardComponent } from "../../components/article-card/article-card.component";
import { GridLayout } from "../../components/layouts/grid-layout/grid.layout";
import { RouterLink } from "@angular/router";
import { ScrollComponent } from "../../components/ui/scroll/scroll.component";
import { HeaderFullComponent } from "../../components/header-full/header-full.component";

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
  ],
  templateUrl: "./home.page.html",
  host: { style: "display: contents;" },
})
export class HomePage {
  protected readonly title = signal("mddweb");
}
