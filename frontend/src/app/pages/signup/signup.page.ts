import { Component, inject, model } from "@angular/core";
import { Router, RouterLink } from "@angular/router";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowLeft } from "@ng-icons/lucide";
import { HeaderComponent } from "@app/components/header/header.component";
import { ThreePartsLayout } from "@app/components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "@app/components/ui/button/button.component";
import { TextInputWithLabelComponent } from "@app/components/ui/text-input-with-label/text-input-with-label.component";
import { AuthService } from "@app/services/auth.service";
import { take } from "rxjs";

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
  private readonly router = inject(Router);

  username = model("");
  email = model("");
  password = model("");

  async onClick() {
    this.authService
      .signUp({
        username: this.username(),
        email: this.email(),
        password: this.password(),
      })
      .pipe(take(1))
      .subscribe({
        next: () => this.router.navigate(["/"]),
        error: console.error,
      });
  }
}
