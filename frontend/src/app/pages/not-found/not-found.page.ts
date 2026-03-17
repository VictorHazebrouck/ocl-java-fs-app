import { Component, signal } from "@angular/core";
import { HeaderFullComponent } from "../../components/header-full/header-full.component";

@Component({
  selector: "not-found-page",
  imports: [HeaderFullComponent],
  templateUrl: "./not-found.page.html",
  styleUrls: [],
})
export class NotFoundPage {
  protected readonly title = signal("mddweb");
}
