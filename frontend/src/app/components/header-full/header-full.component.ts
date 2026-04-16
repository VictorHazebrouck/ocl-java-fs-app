import { Component, signal } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderComponent } from "../header/header.component";
import { ButtonComponent } from "../ui/button/button.component";
import { ModalComponent } from "../ui/modal/modal.component";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideMenu, lucideUser } from "@ng-icons/lucide";

@Component({
  selector: "app-header-full-component",
  imports: [RouterLink, HeaderComponent, ButtonComponent, ModalComponent, NgIcon],
  viewProviders: [provideIcons({ lucideMenu, lucideUser })],
  templateUrl: "./header-full.component.html",
  host: { style: "display: contents;" },
})
export class HeaderFullComponent {
  protected readonly isMobileSidePanelVisible = signal(false);

  closeMobileSidePanel() {
    this.isMobileSidePanelVisible.set(false);
  }

  openMobileSidePanel() {
    this.isMobileSidePanelVisible.set(true);
  }
}
