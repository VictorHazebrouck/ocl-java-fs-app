import { NgClass } from "@angular/common";
import { Component, input, output } from "@angular/core";

@Component({
  selector: "app-modal-component",
  imports: [NgClass],
  templateUrl: "./modal.component.html",
  host: { style: "display: contents;" },
})
export class ModalComponent {
  outerClass = input("");
  innerClass = input("");
  onClose = output<void>();

  protected onBackgroundClick() {
    this.onClose.emit();
  }
}
