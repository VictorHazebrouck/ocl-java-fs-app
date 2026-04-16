import { DatePipe, NgClass } from "@angular/common";
import { Component, input } from "@angular/core";
import { Article, ArticleWithAuthorAndTopic } from "@app/models/article.model";

@Component({
  selector: "app-article-card-component",
  imports: [NgClass, DatePipe],
  templateUrl: "./article-card.component.html",
  host: { style: "display: contents;" },
})
export class ArticleCardComponent {
  public class = input("");

  public article = input<Partial<ArticleWithAuthorAndTopic>>({
    title: "Title",
    createdAt: "Date",
    // author: { username: "Author" },
    content:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
  });
}
