// import { mockSesstion } from "cypress/mocks/mocks";

describe("Delete session spec", () => {
  it("Delete session successful", () => {
    cy.login();
    cy.createSession();
    cy.readSession();
    cy.deleteSession();

    cy.url().should("include", "/sessions");
  });
});
