import { Component } from "@angular/core";
import { ThemeCardComponent } from "@app/components/theme-card/theme-card.component";
import { GridLayout } from "@app/components/layouts/grid-layout/grid.layout";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";

@Component({
  selector: "app-themes-page",
  imports: [HeaderFullComponent, ThemeCardComponent, GridLayout, ScrollComponent],
  templateUrl: "./themes.page.html",
  host: { style: "display: contents;" },
})
export class ThemesPage {}
