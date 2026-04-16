import { Component, inject, model } from "@angular/core";
import { HeaderComponent } from "@app/components/header/header.component";
import { TextInputWithLabelComponent } from "@app/components/ui/text-input-with-label/text-input-with-label.component";
import { ButtonComponent } from "@app/components/ui/button/button.component";
import { ThreePartsLayout } from "@app/components/layouts/three-parts-layout/three-parts.layout";
import { Router, RouterLink } from "@angular/router";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowLeft } from "@ng-icons/lucide";
import { AuthService } from "@app/services/auth.service";

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
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  usernameOrEmail = model("");
  password = model("");

  protected signIn() {
    this.authService
      .signIn({
        usernameOrEmail: this.usernameOrEmail(),
        password: this.password(),
      })
      .subscribe({
        next: () => this.router.navigate(["/"]),
        error: console.error,
      });
  }
}
