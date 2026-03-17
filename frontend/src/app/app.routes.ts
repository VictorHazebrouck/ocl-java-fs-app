import type { Routes } from "@angular/router";
import { SignPage } from "./pages/sign/sign.page";
import { NotFoundPage } from "./pages/not-found/not-found.page";
import { SigninPage } from "./pages/signin/signin.page";
import { SignupPage } from "./pages/signup/signup.page";
import { HomePage } from "./pages/articles(home)/home.page";
import { ArticleCreatePage } from "./pages/article-create/article-create.page";
import { ThemesPage } from "./pages/themes/themes.page";
import { ArticleByIdPage } from "./pages/article-by-id/article-by-id.page";
import { ProfilePage } from "./pages/profile/profile.page";

export const routes: Routes = [
  { path: "", component: HomePage },
  { path: "article-create", component: ArticleCreatePage },
  { path: "article/:id", component: ArticleByIdPage },
  { path: "themes", component: ThemesPage },
  { path: "profile", component: ProfilePage },
  { path: "sign", component: SignPage },
  { path: "signin", component: SigninPage },
  { path: "signup", component: SignupPage },
  { path: "not-found", component: NotFoundPage },
  { path: "**", redirectTo: "not-found" },
];
