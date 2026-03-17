import { Component, signal } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderComponent } from "../header/header.component";
import { ButtonComponent } from "../ui/button/button.component";

@Component({
  selector: "header-full-component",
  imports: [RouterLink, HeaderComponent, ButtonComponent],
  templateUrl: "./header-full.component.html",
  host: { style: "display: contents;" },
})
export class HeaderFullComponent {
  protected readonly title = signal("mddweb");
}
