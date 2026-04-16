import { NgClass } from "@angular/common";
import { Component, input, model } from "@angular/core";

@Component({
  selector: "app-dropdown-component",
  imports: [NgClass],
  templateUrl: "./dropdown.component.html",
  host: { style: "display: contents;" },
})
export class DropdownComponent {
  class = input("");

  name = input("");
  options = input<string[]>([]);
  value = model("");
}
