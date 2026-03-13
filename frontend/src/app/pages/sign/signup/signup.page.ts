import { Component, signal } from "@angular/core";
import { HeaderComponent } from "../../../components/header/header.component";
import { ThreePartsLayout } from "../../../components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "../../../components/ui/button/button.component";
import { TextInputWithLabelComponent } from "../../../components/ui/text-input-with-label/text-input-with-label.component";
import { RouterLink } from "@angular/router";

@Component({
  selector: "signup-page",
  imports: [
    HeaderComponent,
    TextInputWithLabelComponent,
    ButtonComponent,
    ThreePartsLayout,
    RouterLink,
  ],
  templateUrl: "./signup.page.html",
  host: { style: "display: contents;" },
})
export class SignupPage {
  protected readonly title = signal("mddweb");
}
