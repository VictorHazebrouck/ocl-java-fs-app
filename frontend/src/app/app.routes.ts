import type { Routes } from "@angular/router";
import { SignPage } from "./pages/sign/sign.page";
import { NotFoundPage } from "./pages/not-found/not-found.page";
import { SigninPage } from "./pages/signin/signin.page";
import { SignupPage } from "./pages/signup/signup.page";
import { HomePage } from "./pages/articles(home)/home.page";
import { ArticleCreatePage } from "./pages/article-create/article-create.page";
import { TopicsPage } from "./pages/topics/topics.page";
import { ArticleByIdPage } from "./pages/article-by-id/article-by-id.page";
import { ProfilePage } from "./pages/profile/profile.page";
import { authGuard } from "./guards/auth.guard";
import { guestGuard } from "./guards/guest.guard";

export const routes: Routes = [
  { path: "", component: HomePage, canActivate: [authGuard] },
  { path: "article-create", component: ArticleCreatePage, canActivate: [authGuard] },
  { path: "article/:id", component: ArticleByIdPage, canActivate: [authGuard] },
  { path: "themes", component: TopicsPage, canActivate: [authGuard] },
  { path: "profile", component: ProfilePage, canActivate: [authGuard] },

  { path: "sign", component: SignPage, canActivate: [guestGuard] },
  { path: "signin", component: SigninPage, canActivate: [guestGuard] },
  { path: "signup", component: SignupPage, canActivate: [guestGuard] },

  { path: "not-found", component: NotFoundPage },
  { path: "**", redirectTo: "not-found" },
];
