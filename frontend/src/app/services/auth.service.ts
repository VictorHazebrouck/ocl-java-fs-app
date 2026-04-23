import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { AuthSession } from "../models/auth-session.model";
import { User } from "@app/models/user.model";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private http = inject(HttpClient);

  public signUp(input: {
    username: string;
    email: string;
    password: string;
  }): Observable<AuthSession> {
    return this.http
      .post<AuthSession>("/auth/signup", input)
      .pipe(tap((session) => this.storeSession(session)));
  }

  public signIn(input: { usernameOrEmail: string; password: string }): Observable<AuthSession> {
    return this.http
      .post<AuthSession>("/auth/signin", input)
      .pipe(tap((session) => this.storeSession(session)));
  }

  public signOut(): void {
    this.clearTokens();
  }

  public isSignedIn(): boolean {
    return !!this.getAccessToken();
  }

  public refresh(): Observable<AuthSession> {
    return this.http
      .post<AuthSession>("/auth/refresh", { refreshToken: this.getRefreshToken() })
      .pipe(tap((session) => this.storeSession(session)));
  }

  public getProfile(): Observable<User> {
    return this.http.get<User>("/auth/user", {});
  }

  public setUsername(username: string): Observable<User> {
    return this.http.post<User>("/auth/username", {
      newUsername: username,
    });
  }

  public setPassword(password: string): Observable<User> {
    return this.http.post<User>("/auth/password", {
      newPassword: password,
    });
  }

  public getAccessToken(): string | null {
    return localStorage.getItem("accessToken");
  }

  private getRefreshToken(): string | null {
    return localStorage.getItem("refreshToken");
  }

  private storeSession(session: AuthSession) {
    localStorage.setItem("accessToken", session.accessToken);
    localStorage.setItem("refreshToken", session.refreshToken);
  }

  private clearTokens() {
    localStorage.clear();
  }
}
