import { Component, signal } from "@angular/core";
import { HeaderComponent } from "../../../components/header/header.component";
import { TextInputWithLabelComponent } from "../../../components/ui/text-input-with-label/text-input-with-label.component";
import { ButtonComponent } from "../../../components/ui/button/button.component";
import { ThreePartsLayout } from "../../../components/layouts/three-parts-layout/three-parts.layout";
import { RouterLink } from "@angular/router";

@Component({
  selector: "signin-page",
  imports: [
    HeaderComponent,
    TextInputWithLabelComponent,
    ButtonComponent,
    ThreePartsLayout,
    RouterLink,
  ],
  templateUrl: "./signin.page.html",
  host: { style: "display: contents;" },
})
export class SigninPage {
  protected readonly title = signal("mddweb");
}
