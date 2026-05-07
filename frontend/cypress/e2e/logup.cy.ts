import { mockUser } from "cypress/mocks/mocks";

describe("Login spec", () => {
  it("Login successfull", () => {
    cy.visit("/login");
    cy.get('[routerlink="/register"]').click();

    cy.intercept("POST", "/api/auth/register", {
      body: mockUser,
    });

    cy.intercept(
      {
        method: "GET",
        url: "/api/session",
      },
      [],
    ).as("session");

    cy.get("input[formControlName=firstName]").type(mockUser.firstName);
    cy.get("input[formControlName=lastName]").type(mockUser.lastName);
    cy.get("input[formControlName=email]").type("mddweb@studio.com");
    cy.get("input[formControlName=password]").type(`${"test!1234"}{enter}{enter}`);

    cy.url().should("include", "/login");
  });
});
