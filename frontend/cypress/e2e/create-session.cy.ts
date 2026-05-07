import { mockSesstion } from "cypress/mocks/mocks";

describe("Create session spec", () => {
  it("Create session successful", () => {
    cy.login();
    cy.createSession();

    cy.url().should("include", "/sessions");
    cy.get("p").should("contain.text", mockSesstion.description);
  });
});
