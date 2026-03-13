import { NgClass } from "@angular/common";
import { Component, input } from "@angular/core";

export type Variant = "outline" | "primary" | "transparent";

@Component({
  selector: "button-component",
  imports: [NgClass],
  templateUrl: "./button.component.html",
  host: { style: "display: contents;" },
})
export class ButtonComponent {
  class = input("");
  variant = input<Variant>("outline");
}
