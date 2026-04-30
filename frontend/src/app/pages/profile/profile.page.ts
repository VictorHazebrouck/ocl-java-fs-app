import { Component, inject, model, signal } from "@angular/core";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { ThemeCardComponent } from "@app/components/theme-card/theme-card.component";
import { TextInputComponent } from "@app/components/ui/text-input/text-input.component";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { GridLayout } from "@app/components/layouts/grid-layout/grid.layout";
import { ButtonComponent } from "@app/components/ui/button/button.component";
import { ThreePartsLayout } from "@app/components/layouts/three-parts-layout/three-parts.layout";

import { TopicService } from "@app/services/topic.service";
import { Topic } from "@app/models/topic.model";
import { AuthService } from "@app/services/auth.service";
import { take } from "rxjs";

@Component({
  selector: "app-profile-page",
  imports: [
    HeaderFullComponent,
    ThemeCardComponent,
    TextInputComponent,
    ScrollComponent,
    GridLayout,
    ButtonComponent,
    ThreePartsLayout,
  ],
  templateUrl: "./profile.page.html",
  styleUrls: [],
})
export class ProfilePage {
  private readonly topicService = inject(TopicService);
  private readonly authService = inject(AuthService);

  protected topics = signal<Topic[]>([]);

  protected username = model("");
  protected email = model("");
  protected password = model("");

  constructor() {
    this.refreshTopics();
    this.refreshUser();
  }

  protected onClick() {
    if (this.password()) {
      this.changePassword();
    }
    this.changeUsername();
  }

  protected unsubscribeFromTopic(id: string) {
    this.topicService
      .deleteTopicSubscription(id)
      .pipe(take(1))
      .subscribe({
        next: () => this.refreshTopics(),
        error: console.error,
      });
  }

  private changeUsername() {
    this.authService
      .setUsername(this.username())
      .pipe(take(1))
      .subscribe({
        next: () => this.refreshUser(),
        error: console.error,
      });
  }

  private changePassword() {
    this.authService
      .setPassword(this.password())
      .pipe(take(1))
      .subscribe({
        next: () => this.password.set(""),
        error: console.error,
      });
  }

  private refreshTopics() {
    this.topicService
      .getSubscribedTopics()
      .pipe(take(1))
      .subscribe({
        next: (topics) => this.topics.set(topics),
        error: console.error,
      });
  }

  private refreshUser() {
    this.authService
      .getProfile()
      .pipe(take(1))
      .subscribe({
        next: (p) => {
          console.log(p.username);
          this.username.set(p.username);
          this.email.set(p.email);
        },
        error: console.error,
      });
  }
}
