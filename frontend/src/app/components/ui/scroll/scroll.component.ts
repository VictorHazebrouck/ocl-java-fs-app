import { NgClass } from "@angular/common";
import { Component, input } from "@angular/core";

@Component({
  selector: "app-scroll-component",
  imports: [NgClass],
  templateUrl: "./scroll.component.html",
  host: { style: "display: contents;" },
})
export class ScrollComponent {
  class = input("");
}
