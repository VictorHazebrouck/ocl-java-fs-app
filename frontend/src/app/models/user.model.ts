import { Id, ISOStringDate } from "./utils";

export interface User {
  id: Id;
  username: string;
  email: string;
  token: string;
  createdAt: ISOStringDate;
  updatedAt: ISOStringDate;
}
