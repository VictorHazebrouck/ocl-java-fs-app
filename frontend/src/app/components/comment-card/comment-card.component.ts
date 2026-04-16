import { NgClass } from "@angular/common";
import { Component, input } from "@angular/core";

@Component({
  selector: "app-comment-card-component",
  imports: [NgClass],
  templateUrl: "./comment-card.component.html",
  host: { style: "display: contents;" },
})
export class CommentCardComponent {
  class = input("");
  username = input("Username");
  content = input("Contenu du commentaire");
}
