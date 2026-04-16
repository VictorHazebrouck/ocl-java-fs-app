import { Topic } from "./topic.model";
import { User } from "./user.model";
import { Id } from "./utils";

export interface Article {
  id: Id;
  title: string;
  content: string;
  author: Id;
  topic: Id;
  createdAt: string;
  updatedAt: string;
}

export interface ArticleWithAuthor extends Omit<Article, "author"> {
  author: User;
}

export interface ArticleWithTopic extends Omit<Article, "topic"> {
  topic: Topic;
}

export interface ArticleWithAuthorAndTopic extends Omit<Article, "author" | "topic"> {
  author: User;
  topic: Topic;
}

export interface ArticleCreation extends Pick<Article, "title" | "content"> {
  topicId: string;
}
