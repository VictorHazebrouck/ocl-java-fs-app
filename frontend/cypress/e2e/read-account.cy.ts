import { mockUser } from "cypress/mocks/mocks";

describe("Read Account spec", () => {
  it("Read Account successfull", () => {
    cy.login();

    cy.intercept(
      {
        method: "GET",
        url: "/api/user/1",
      },
      mockUser,
    ).as("sessions");
    cy.get('[routerlink="me"]').click();

    cy.url().should("include", "/me");
  });
});
