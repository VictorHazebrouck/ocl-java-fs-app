import { Component, signal } from "@angular/core";
import { HeaderComponent } from "../../components/header/header.component";
import { ThreePartsLayout } from "../../components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "../../components/ui/button/button.component";
import { TextInputComponent } from "../../components/ui/text-input/text-input.component";
import { RouterLink } from "@angular/router";
import { TextareaComponent } from "../../components/ui/textarea/textarea.component";

@Component({
  selector: "article-by-id-page",
  imports: [
    HeaderComponent,
    ThreePartsLayout,
    ButtonComponent,
    TextInputComponent,
    RouterLink,
    TextareaComponent,
  ],
  templateUrl: "./article-by-id.page.html",
  host: { style: "display: contents;" },
})
export class ArticleByIdPage {
  protected readonly title = signal("mddweb");
}
