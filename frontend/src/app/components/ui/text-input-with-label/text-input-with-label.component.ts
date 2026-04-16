import { NgClass } from "@angular/common";
import { Component, input, model } from "@angular/core";

@Component({
  selector: "app-text-input-with-label-component",
  imports: [NgClass],
  templateUrl: "./text-input-with-label.component.html",
  host: { style: "display: contents;" },
})
export class TextInputWithLabelComponent {
  class = input("");
  label = input("input label");
  placeholder = input("");
  value = model("");
}
