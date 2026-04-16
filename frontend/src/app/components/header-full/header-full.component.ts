import { Component, inject, signal } from "@angular/core";
import { Router, RouterLink } from "@angular/router";
import { HeaderComponent } from "../header/header.component";
import { ButtonComponent } from "../ui/button/button.component";
import { ModalComponent } from "../ui/modal/modal.component";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideMenu, lucideUser } from "@ng-icons/lucide";
import { AuthService } from "@app/services/auth.service";

@Component({
  selector: "app-header-full-component",
  imports: [RouterLink, HeaderComponent, ButtonComponent, ModalComponent, NgIcon],
  viewProviders: [provideIcons({ lucideMenu, lucideUser })],
  templateUrl: "./header-full.component.html",
  host: { style: "display: contents;" },
})
export class HeaderFullComponent {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected isMobileSidePanelVisible = signal(false);

  protected signOut() {
    this.authService.signOut();
    this.router.navigate(["/sign"]);
  }

  protected closeMobileSidePanel() {
    this.isMobileSidePanelVisible.set(false);
  }

  protected openMobileSidePanel() {
    this.isMobileSidePanelVisible.set(true);
  }
}
