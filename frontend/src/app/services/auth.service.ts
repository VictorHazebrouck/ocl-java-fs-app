import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { AuthSession } from "../models/auth-session";

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
      .post<AuthSession>("/api/auth/signup", input)
      .pipe(tap((session) => this.storeSession(session)));
  }

  public signIn(input: { usernameOrEmail: string; password: string }): Observable<AuthSession> {
    return this.http
      .post<AuthSession>("/auth/signin", input)
      .pipe(tap((session) => this.storeSession(session)));
  }

  public signOut(): void {
    return this.clearTokens();
  }

  public isSignedIn(): boolean {
    return !!this.getAccessToken();
  }

  public refresh(): Observable<{ accessToken: string }> {
    return this.http
      .post<{ accessToken: string }>("/api/auth/refresh", {
        refreshToken: this.getRefreshToken(),
      })
      .pipe(
        tap((res) => {
          localStorage.setItem("accessToken", res.accessToken);
        }),
      );
  }

  public getProfile(): Observable<AuthSession> {
    return this.http.post<AuthSession>("/api/auth/profile", {});
  }

  private storeSession(session: AuthSession) {
    localStorage.setItem("accessToken", session.accessToken);
    localStorage.setItem("refreshToken", session.refreshToken);
  }

  public getAccessToken(): string | null {
    return localStorage.getItem("accessToken");
  }

  public getRefreshToken(): string | null {
    return localStorage.getItem("refreshToken");
  }

  private clearTokens() {
    localStorage.clear();
  }
}
