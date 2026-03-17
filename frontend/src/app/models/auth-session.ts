import { ISOStringDate } from "./utils";

export interface AuthSession {
  username: string;
  email: string;
  token: string;
  createdAt: ISOStringDate;
  updatedAt: ISOStringDate;
}
