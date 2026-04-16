import { Component, inject, model } from "@angular/core";
import { HeaderComponent } from "../../components/header/header.component";
import { TextInputWithLabelComponent } from "../../components/ui/text-input-with-label/text-input-with-label.component";
import { ButtonComponent } from "../../components/ui/button/button.component";
import { ThreePartsLayout } from "../../components/layouts/three-parts-layout/three-parts.layout";
import { RouterLink } from "@angular/router";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowLeft } from "@ng-icons/lucide";
import { AuthService } from "../../services/auth.service";

@Component({
  selector: "app-signin-page",
  imports: [
    HeaderComponent,
    TextInputWithLabelComponent,
    ButtonComponent,
    ThreePartsLayout,
    RouterLink,
    NgIcon,
  ],
  viewProviders: [provideIcons({ lucideArrowLeft })],
  templateUrl: "./signin.page.html",
  host: { style: "display: contents;" },
})
export class SigninPage {
  private authService = inject(AuthService);

  usernameOrEmail = model("");
  password = model("");

  protected async signIn() {
    this.authService.signIn({
      usernameOrEmail: this.usernameOrEmail(),
      password: this.password(),
    });
  }
}
