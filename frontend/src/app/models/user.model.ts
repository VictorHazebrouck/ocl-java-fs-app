import { Id, ISOStringDate } from "./utils";

export interface User {
  id: Id;
  username: string;
  email: string;
  createdAt: ISOStringDate;
  updatedAt: ISOStringDate;
}
