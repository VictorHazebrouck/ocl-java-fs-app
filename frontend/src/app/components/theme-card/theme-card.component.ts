import { NgClass } from "@angular/common";
import { Component, inject, input, output } from "@angular/core";
import { ButtonComponent } from "../ui/button/button.component";
import { TopicWithAmISubscribed } from "@app/models/topic.model";
import { TopicService } from "@app/services/topic.service";

@Component({
  selector: "app-theme-card-component",
  imports: [NgClass, ButtonComponent],
  templateUrl: "./theme-card.component.html",
  host: { style: "display: contents;" },
})
export class ThemeCardComponent {
  class = input("");
  topic = input<Partial<TopicWithAmISubscribed>>({
    name: "Tiyle",
    description: "JAJJAJAJ",
    amISubscribed: true,
  });
  onClickSubscribe = output<void>();
  onClickUnubscribe = output<void>();
}
