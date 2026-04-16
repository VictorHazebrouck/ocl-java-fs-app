import { Component, signal } from "@angular/core";
import { RouterLink } from "@angular/router";

@Component({
  selector: "app-header-component",
  imports: [RouterLink],
  templateUrl: "./header.component.html",
  host: { style: "display: contents;" },
})
export class HeaderComponent {
  protected readonly title = signal("mddweb");
}
