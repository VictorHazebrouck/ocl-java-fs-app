import { User } from "./user.model";
import { Id, ISOStringDate } from "./utils";

export interface Comment {
  id: Id;
  article: Id;
  author: Id;
  content: string;
  createdAt: ISOStringDate;
  updatedAt: ISOStringDate;
}

export interface CommentWithAuthor extends Omit<Comment, "author"> {
  author: User;
}
