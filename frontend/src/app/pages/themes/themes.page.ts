import { Component, signal } from "@angular/core";
import { HeaderComponent } from "../../components/header/header.component";
import { ThreePartsLayout } from "../../components/layouts/three-parts-layout/three-parts.layout";
import { ButtonComponent } from "../../components/ui/button/button.component";
import { TextInputComponent } from "../../components/ui/text-input/text-input.component";
import { RouterLink } from "@angular/router";
import { TextareaComponent } from "../../components/ui/textarea/textarea.component";
import { ThemeCardComponent } from "../../components/theme-card/theme-card.component";
import { GridLayout } from "../../components/layouts/grid-layout/grid.layout";
import { ScrollComponent } from "../../components/ui/scroll/scroll.component";
import { HeaderFullComponent } from "../../components/header-full/header-full.component";

@Component({
  selector: "themes-page",
  imports: [HeaderFullComponent, ThemeCardComponent, GridLayout, ScrollComponent],
  templateUrl: "./themes.page.html",
  host: { style: "display: contents;" },
})
export class ThemesPage {
  protected readonly title = signal("mddweb");
}
