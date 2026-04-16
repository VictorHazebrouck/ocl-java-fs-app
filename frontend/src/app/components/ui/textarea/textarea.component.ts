import { NgClass } from "@angular/common";
import { Component, input } from "@angular/core";

@Component({
  selector: "app-textarea-component",
  imports: [NgClass],
  templateUrl: "./textarea.component.html",
  host: { style: "display: contents;" },
})
export class TextareaComponent {
  class = input("");
  name = input("");
  placeholder = input("");
}
