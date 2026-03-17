import { HttpClient } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable } from "rxjs";
import { Id } from "../models/utils";
import { Topic, TopicWithAmISubscribed } from "../models/topic.model";

export class TopicService {
  private http = inject(HttpClient);

  /** for `/create-article` page */
  public topicGet(): Observable<Topic[]> {
    return this.http.get<Topic[]>("/api/topic");
  }

  /** for `/topic` page */
  public topicGetWithAmISubscribed(): Observable<TopicWithAmISubscribed[]> {
    return this.http.get<TopicWithAmISubscribed[]>("/api/topic/am-i-subscribed");
  }

  /**  for `/profile` page */
  public topicGetOnlySubscribed(): Observable<TopicWithAmISubscribed[]> {
    return this.http.get<TopicWithAmISubscribed[]>("/api/topic/only-subscribed");
  }

  public topicSubscriptionCreate(topicId: Id): Observable<void> {
    return this.http.post<void>(`/api/topic/subscription/${topicId}`, {});
  }

  public topicSubscriptionRemove(topicId: Id): Observable<void> {
    return this.http.delete<void>(`/api/topic/subscription/${topicId}`);
  }
}
