import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Id } from "../models/utils";
import { Topic, TopicWithAmISubscribed } from "../models/topic.model";

@Injectable({
  providedIn: "root",
})
export class TopicService {
  private http = inject(HttpClient);

  /** for `/create-article` page */
  public getTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>("/api/topic");
  }

  /** for `/topic` page */
  public getTopicsWithSubscriptionInfo(): Observable<TopicWithAmISubscribed[]> {
    return this.http.get<TopicWithAmISubscribed[]>("/api/topic/with-am-i-subscribed");
  }

  /**  for `/profile` page */
  public getSubscribedTopics(): Observable<Topic[]> {
    return this.http.get<TopicWithAmISubscribed[]>("/api/topic/only-subscribed");
  }

  public createTopicSubscription(topicId: Id): Observable<void> {
    return this.http.post<void>(`/api/subscription/${topicId}`, {});
  }

  public deleteTopicSubscription(topicId: Id): Observable<void> {
    return this.http.delete<void>(`/api/subscription/${topicId}`);
  }
}
