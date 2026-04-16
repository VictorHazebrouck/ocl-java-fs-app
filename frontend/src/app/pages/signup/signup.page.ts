import { Component, inject, model } from "@angular/core";
import { RouterLink } from "@angular/router";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowLeft } from "@ng-icons/lucide";
import { HeaderComponent } from "../../components/header/header.component";
import { ThreePartsLayout } from "../../components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "../../components/ui/button/button.component";
import { TextInputWithLabelComponent } from "../../components/ui/text-input-with-label/text-input-with-label.component";
import { AuthService } from "../../services/auth.service";

@Component({
  selector: "app-signup-page",
  imports: [
    HeaderComponent,
    TextInputWithLabelComponent,
    ButtonComponent,
    ThreePartsLayout,
    RouterLink,
    NgIcon,
  ],
  viewProviders: [provideIcons({ lucideArrowLeft })],
  templateUrl: "./signup.page.html",
  host: { style: "display: contents;" },
})
export class SignupPage {
  private readonly authService = inject(AuthService);

  username = model("");
  email = model("");
  password = model("");

  async onClick() {
    this.authService.signUp({
      username: this.username(),
      email: this.email(),
      password: this.password(),
    });
  }
}
