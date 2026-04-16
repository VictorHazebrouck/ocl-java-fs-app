import { Component, inject } from "@angular/core";
import { ThemeCardComponent } from "@app/components/theme-card/theme-card.component";
import { GridLayout } from "@app/components/layouts/grid-layout/grid.layout";
import { ScrollComponent } from "@app/components/ui/scroll/scroll.component";
import { HeaderFullComponent } from "@app/components/header-full/header-full.component";
import { TopicService } from "@app/services/topic.service";
import { toSignal } from "@angular/core/rxjs-interop";

@Component({
  selector: "app-topics-page",
  imports: [HeaderFullComponent, ThemeCardComponent, GridLayout, ScrollComponent],
  templateUrl: "./topics.page.html",
  host: { style: "display: contents;" },
})
export class ThemesPage {
  private readonly topicService = inject(TopicService);

  protected topics = toSignal(this.topicService.getTopics(), { initialValue: [] });
}
