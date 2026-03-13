import { Component, signal } from "@angular/core";

@Component({
  selector: "not-found-page",
  standalone: true,
  templateUrl: "./not-found.page.html",
  styleUrls: [],
})
export class NotFoundPage {
  protected readonly title = signal("mddweb");
}
