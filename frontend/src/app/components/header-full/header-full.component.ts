import { Component, signal } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderComponent } from "../header/header.component";
import { ButtonComponent } from "../ui/button/button.component";
import { ModalComponent } from "../ui/modal/modal.component";

@Component({
  selector: "header-full-component",
  imports: [RouterLink, HeaderComponent, ButtonComponent, ModalComponent],
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
