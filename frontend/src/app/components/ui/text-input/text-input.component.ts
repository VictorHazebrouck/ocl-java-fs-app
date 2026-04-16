import { NgClass } from "@angular/common";
import { Component, input, model } from "@angular/core";

@Component({
  selector: "app-text-input-component",
  imports: [NgClass],
  templateUrl: "./text-input.component.html",
  host: { style: "display: contents;" },
})
export class TextInputComponent {
  class = input("");
  name = input("");
  placeholder = input("");
  value = model("");
}
