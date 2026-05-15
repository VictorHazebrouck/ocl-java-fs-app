export const mockSession = {
  accessToken: "ajjajajajaja",
  refreshToken: "aakakakakak",
};

export const mockUser = {
  id: "12345678",
  username: "john",
  email: "john@john.com",
  emailVerified: false,
  createdAt: new Date().toISOString(),
  updatedAt: new Date().toISOString(),
};

export const mockTopic = {
  id: "12345678",
  name: "Foo",
  description: "ajbjcbhecbhe",
  createdAt: new Date().toISOString(),
  updatedAt: new Date().toISOString(),
};
export const mockTopic2 = {
  id: "23456789",
  name: "Bar",
  description: "cjwehibewcbew",
  createdAt: new Date().toISOString(),
  updatedAt: new Date().toISOString(),
};

export const mockArticle = {
  id: "12345678",
  title: "New article name",
  content: "New article desc",
  author: mockUser,
  topic: mockTopic,
  createdAt: new Date("01/01/2000").toISOString(),
  updatedAt: new Date("01/01/2000").toISOString(),
};

export const mockArticle2 = {
  id: "12345679",
  title: "New article name2",
  content: "New article desc2",
  author: mockUser,
  topic: mockTopic,
  createdAt: new Date("02/01/2000").toISOString(),
  updatedAt: new Date("02/01/2000").toISOString(),
};

export const mockComment = {
  id: "12345678",
  article: "12345678",
  author: mockUser,
  content: "Test comment",
  createdAt: new Date().toISOString(),
  updatedAt: new Date().toISOString(),
};
