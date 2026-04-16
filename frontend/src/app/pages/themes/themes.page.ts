import { Component } from "@angular/core";
import { ThemeCardComponent } from "../../components/theme-card/theme-card.component";
import { GridLayout } from "../../components/layouts/grid-layout/grid.layout";
import { ScrollComponent } from "../../components/ui/scroll/scroll.component";
import { HeaderFullComponent } from "../../components/header-full/header-full.component";

@Component({
  selector: "app-themes-page",
  imports: [HeaderFullComponent, ThemeCardComponent, GridLayout, ScrollComponent],
  templateUrl: "./themes.page.html",
  host: { style: "display: contents;" },
})
export class ThemesPage {}
