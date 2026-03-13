import { NgClass } from "@angular/common";
import { Component, input } from "@angular/core";

@Component({
  selector: "three-parts-layout",
  imports: [NgClass],
  templateUrl: "./three-parts.layout.html",
  host: { style: "display: contents;" },
})
export class ThreePartsLayout {
  class = input("");
}
