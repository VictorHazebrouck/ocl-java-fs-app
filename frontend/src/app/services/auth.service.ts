import { HttpClient } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable } from "rxjs";
import { AuthSession } from "../models/auth-session";

export class AuthService {
  private http = inject(HttpClient);

  public signIn(input: { usernameOrEmail: string; password: string }): Observable<AuthSession> {
    return this.http.post<AuthSession>("/api/sign/signin", input);
  }

  public signUp(input: {
    username: string;
    email: string;
    password: string;
  }): Observable<AuthSession> {
    return this.http.post<AuthSession>("/api/sign/signup", input);
  }

  public refresd(): Observable<void> {
    return this.http.post<void>("/api/sign/refresh", {});
  }

  public profileGet(): Observable<AuthSession> {
    return this.http.post<AuthSession>("/api/sign/profile", {});
  }
}
