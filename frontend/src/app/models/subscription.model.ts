import { Id, ISOStringDate } from "./utils";

export interface Subscription {
  id: Id;
  user: Id;
  topic: Id;
  createdAt: ISOStringDate;
  updatedAt: ISOStringDate;
}
