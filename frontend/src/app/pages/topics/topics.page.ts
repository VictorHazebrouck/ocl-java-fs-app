import { Component, inject, signal } from "@angular/core";
import { ThemeCardComponent } from "@app/components/theme-card/theme-card.component";
import { GridLayout } from "@app/components/layouts/grid-layout/grid.layout";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { TopicService } from "@app/services/topic.service";
import { TopicWithAmISubscribed } from "@app/models/topic.model";

@Component({
  selector: "app-topics-page",
  imports: [HeaderFullComponent, ThemeCardComponent, GridLayout, ScrollComponent],
  templateUrl: "./topics.page.html",
  host: { style: "display: contents;" },
})
export class ThemesPage {
  private readonly topicService = inject(TopicService);

  protected topics = signal<TopicWithAmISubscribed[]>([]);

  constructor() {
    this.refreshTopics();
  }

  protected subscribeToTopic(id: string) {
    this.topicService.createTopicSubscription(id).subscribe({
      next: () => this.refreshTopics(),
      error: console.error,
    });
  }

  protected unsubscribeFromTopic(id: string) {
    console.log("kakaka");

    this.topicService.deleteTopicSubscription(id).subscribe({
      next: () => this.refreshTopics(),
      error: console.error,
    });
  }

  private refreshTopics() {
    this.topicService.getTopicsWithSubscriptionInfo().subscribe({
      next: (topics) => this.topics.set(topics),
    });
  }
}
