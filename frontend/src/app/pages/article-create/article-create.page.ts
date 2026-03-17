import { Component, signal } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderFullComponent } from "../../components/header-full/header-full.component";
import { ThreePartsLayout } from "../../components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "../../components/ui/button/button.component";
import { TextInputComponent } from "../../components/ui/text-input/text-input.component";
import { TextareaComponent } from "../../components/ui/textarea/textarea.component";

@Component({
  selector: "article-create-page",
  imports: [
    HeaderFullComponent,
    ThreePartsLayout,
    ButtonComponent,
    TextInputComponent,
    RouterLink,
    TextareaComponent,
  ],
  templateUrl: "./article-create.page.html",
  host: { style: "display: contents;" },
})
export class ArticleCreatePage {
  protected readonly title = signal("mddweb");
}
