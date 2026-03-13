import { Component, signal } from "@angular/core";
import { HeaderComponent } from "../../components/header/header.component";
import { ThreePartsLayout } from "../../components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "../../components/ui/button/button.component";
import { ArticleCardComponent } from "../../components/article-card/article-card.component";
import { GridLayout } from "../../components/layouts/grid-layout/grid.layout";
import { TextInputComponent } from "../../components/ui/text-input/text-input.component";
import { RouterLink } from "@angular/router";

@Component({
  selector: "article-create-page",
  imports: [HeaderComponent, ThreePartsLayout, ButtonComponent, TextInputComponent, RouterLink],
  templateUrl: "./article-create.page.html",
  host: { style: "display: contents;" },
})
export class ArticleCreatePage {
  protected readonly title = signal("mddweb");
}
