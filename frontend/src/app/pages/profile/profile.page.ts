import { Component } from "@angular/core";
import { HeaderFullComponent } from "../../components/header-full/header-full.component";
import { ThemeCardComponent } from "../../components/theme-card/theme-card.component";
import { TextInputComponent } from "../../components/ui/text-input/text-input.component";
import { ScrollComponent } from "../../components/ui/scroll/scroll.component";
import { GridLayout } from "../../components/layouts/grid-layout/grid.layout";
import { ButtonComponent } from "../../components/ui/button/button.component";
import { ThreePartsLayout } from "../../components/layouts/three-parts-layout/three-parts.layout";

@Component({
  selector: "app-profile-page",
  imports: [
    HeaderFullComponent,
    ThemeCardComponent,
    TextInputComponent,
    ScrollComponent,
    GridLayout,
    ButtonComponent,
    ThreePartsLayout,
  ],
  templateUrl: "./profile.page.html",
  styleUrls: [],
})
export class ProfilePage {}
