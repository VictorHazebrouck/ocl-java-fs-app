import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { ButtonComponent } from "../../components/ui/button/button.component";

@Component({
  selector: "app-sign-page",
  imports: [RouterLink, ButtonComponent],
  templateUrl: "./sign.page.html",
  host: { style: "display: contents;" },
})
export class SignPage {}
