import { Id, ISOStringDate } from "./utils";

export interface Topic {
  id: Id;
  name: string;
  description: string;
  createdAt: ISOStringDate;
  updatedAt: ISOStringDate;
}

export interface TopicWithAmISubscribed extends Topic {
  amISubscribed: boolean;
}
