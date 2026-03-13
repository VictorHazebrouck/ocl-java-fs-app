import { NgClass } from "@angular/common";
import { Component, input } from "@angular/core";

@Component({
  selector: "article-card-component",
  imports: [NgClass],
  templateUrl: "./article-card.component.html",
  host: { style: "display: contents;" },
})
export class ArticleCardComponent {
  class = input("");
  title = input("Title");
  date = input("Date");
  author = input("Author");
  content = input("Content");
}
