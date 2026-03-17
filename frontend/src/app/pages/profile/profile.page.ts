import { Component, signal } from "@angular/core";
import { HeaderFullComponent } from "../../components/header-full/header-full.component";

@Component({
  selector: "profile-page",
  imports: [HeaderFullComponent],
  templateUrl: "./profile.page.html",
  styleUrls: [],
})
export class ProfilePage {
  protected readonly title = signal("mddweb");
}
