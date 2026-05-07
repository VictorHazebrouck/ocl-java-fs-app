// import { mockSesstion } from "cypress/mocks/mocks";

describe("Read session spec", () => {
  it("Read session successful", () => {
    cy.login();
    cy.createSession();
    cy.readSession();

    cy.url().should("include", "/sessions/detail/1");
  });

  it("Read session and go back", () => {
    cy.login();
    cy.createSession();
    cy.readSession();

    cy.get(
      "body > app-root > div > div > app-detail > div > mat-card > mat-card-title > div > div:nth-child(1) > button",
    ).click();

    cy.url().should("include", "/sessions");
  });

  it("Read session and go back", () => {
    cy.login();
    cy.createSession();
    cy.readSession();

    cy.get(
      "body > app-root > div > div > app-detail > div > mat-card > mat-card-title > div > div:nth-child(1) > button",
    ).click();

    cy.url().should("include", "/sessions");
  });
});
