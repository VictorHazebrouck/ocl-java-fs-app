import { Component } from "@angular/core";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { ThemeCardComponent } from "@app/components/theme-card/theme-card.component";
import { TextInputComponent } from "@app/components/ui/text-input/text-input.component";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { GridLayout } from "@app/components/layouts/grid-layout/grid.layout";
import { ButtonComponent } from "@app/components/ui/button/button.component";
import { ThreePartsLayout } from "@app/components/layouts/three-parts-layout/three-parts.layout";

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
