import { Component, inject, signal } from "@angular/core";
import { ThemeCardComponent } from "@app/components/theme-card/theme-card.component";
import { GridLayout } from "@app/components/layouts/grid-layout/grid.layout";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { TopicService } from "@app/services/topic.service";
import { TopicWithAmISubscribed } from "@app/models/topic.model";
import { take } from "rxjs";

@Component({
  selector: "app-topics-page",
  imports: [HeaderFullComponent, ThemeCardComponent, GridLayout, ScrollComponent],
  templateUrl: "./topics.page.html",
  host: { style: "display: contents;" },
})
export class TopicsPage {
  private readonly topicService = inject(TopicService);

  protected topics = signal<TopicWithAmISubscribed[]>([]);

  constructor() {
    this.refreshTopics();
  }

  protected subscribeToTopic(id: string) {
    this.topicService
      .createTopicSubscription(id)
      .pipe(take(1))
      .subscribe({
        next: () => this.refreshTopics(),
        error: console.error,
      });
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

  private refreshTopics() {
    this.topicService
      .getTopicsWithSubscriptionInfo()
      .pipe(take(1))
      .subscribe({
        next: (topics) => this.topics.set(topics),
      });
  }
}
