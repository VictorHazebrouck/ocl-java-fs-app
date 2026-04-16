import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { ThreePartsLayout } from "@app/components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "@app/components/ui/button/button.component";
import { TextInputComponent } from "@app/components/ui/text-input/text-input.component";
import { TextareaComponent } from "@app/components/ui/textarea/textarea.component";
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
export class ArticleCreatePage {}
