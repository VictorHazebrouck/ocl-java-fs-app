import { NgClass } from "@angular/common";
import { Component, inject, input, output } from "@angular/core";
import { ButtonComponent, Variant } from "../ui/button/button.component";
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
  subscribedTitle = input("Déjà abonné");
  notSubscribedTitle = input("S'abonner");
  buttonVariant = input<Variant>("primary");
  topic = input<Partial<TopicWithAmISubscribed>>({
    name: "",
    description: "",
    amISubscribed: true,
  });

  onClickSubscribe = output<void>();
  onClickUnubscribe = output<void>();

  protected onClick() {
    if (this.topic().amISubscribed) {
      this.onClickUnubscribe.emit();
    } else {
      this.onClickSubscribe.emit();
    }
  }
}
