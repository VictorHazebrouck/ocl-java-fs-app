import { Component, inject, signal } from "@angular/core";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { ThemeCardComponent } from "@app/components/theme-card/theme-card.component";
import { TextInputComponent } from "@app/components/ui/text-input/text-input.component";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { GridLayout } from "@app/components/layouts/grid-layout/grid.layout";
import { ButtonComponent } from "@app/components/ui/button/button.component";
import { ThreePartsLayout } from "@app/components/layouts/three-parts-layout/three-parts.layout";

import { TopicService } from "@app/services/topic.service";
import { Topic } from "@app/models/topic.model";

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

  protected topics = signal<Topic[]>([]);

  constructor() {
    this.refreshTopics();
  }

  protected unsubscribeFromTopic(id: string) {
    this.topicService.deleteTopicSubscription(id).subscribe({
      next: () => this.refreshTopics(),
      error: console.error,
    });
  }

  private refreshTopics() {
    this.topicService.getSubscribedTopics().subscribe({
      next: (topics) => this.topics.set(topics),
      error: console.error,
    });
  }
}
