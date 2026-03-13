import { Component, signal } from "@angular/core";
import { RouterLink } from "@angular/router";
import { ButtonComponent } from "../../components/ui/button/button.component";

@Component({
  selector: "sign-page",
  imports: [RouterLink, ButtonComponent],
  templateUrl: "./sign.page.html",
  host: { style: "display: contents;" },
})
export class SignPage {
  protected readonly title = signal("mddweb");
}
