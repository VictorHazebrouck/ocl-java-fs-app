import type { Session } from "src/app/core/models/session.interface";
import type { SessionInformation } from "src/app/core/models/sessionInformation.interface";
import type { Teacher } from "src/app/core/models/teacher.interface";

export const mockTeacher: Teacher = {
  id: 1,
  firstName: "John",
  lastName: "Doe",
  createdAt: new Date(),
  updatedAt: new Date(),
};

export const mockSesstion: Session = {
  id: 1,
  date: new Date(),
  description: "MySession",
  name: "Hahahhaha",
  teacher_id: mockTeacher.id,
  users: [],
  createdAt: new Date(),
  updatedAt: new Date(),
};

export const mockUser: SessionInformation = {
  id: 1,
  username: "userName",
  firstName: "firstName",
  lastName: "lastName",
  admin: true,
  token: "1",
  type: "1",
};
