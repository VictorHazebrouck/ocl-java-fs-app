import { NgClass } from "@angular/common";
import { Component, input } from "@angular/core";

@Component({
  selector: "app-grid-layout",
  imports: [NgClass],
  templateUrl: "./grid.layout.html",
  host: { style: "display: contents;" },
})
export class GridLayout {
  class = input("");
}
