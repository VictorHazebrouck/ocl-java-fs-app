import { NgClass } from "@angular/common";
import { Component, input } from "@angular/core";
import { ButtonComponent } from "../ui/button/button.component";

@Component({
  selector: "app-theme-card-component",
  imports: [NgClass, ButtonComponent],
  templateUrl: "./theme-card.component.html",
  host: { style: "display: contents;" },
})
export class ThemeCardComponent {
  class = input("");
  title = input("Title");
  description = input(
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
  );
  isSubscribed = input(false);
}
