import { Component, input } from "@angular/core";
import { NgIcon, provideIcons } from "@ng-icons/core";
import { lucideArrowLeft, lucideSend } from "@ng-icons/lucide";
import { CommentCardComponent } from "../../components/comment-card/comment-card.component";
import { HeaderFullComponent } from "../../components/header-full/header-full.component";
import { ScrollComponent } from "../../components/ui/scroll/scroll.component";
import { TextareaComponent } from "../../components/ui/textarea/textarea.component";
import { RouterLink } from "@angular/router";

@Component({
  selector: "app-article-by-id-page",
  imports: [
    HeaderFullComponent,
    ScrollComponent,
    NgIcon,
    CommentCardComponent,
    TextareaComponent,
    RouterLink,
  ],
  viewProviders: [provideIcons({ lucideArrowLeft, lucideSend })],
  templateUrl: "./article-by-id.page.html",
  host: { style: "display: contents;" },
})
export class ArticleByIdPage {
  class = input("");
  title = input("Title");
  date = input("Date");
  author = input("Author");
  topic = input("Topic");
  content = input(
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
  );
}
