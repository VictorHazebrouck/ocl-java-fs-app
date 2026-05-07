import { mockSesstion, mockTeacher, mockUser } from "cypress/mocks/mocks";

declare global {
  namespace Cypress {
    interface Chainable {
      login(): typeof login;
      createSession(): typeof createSession;
      readSession(): typeof readSession;
      deleteSession(): typeof deleteSession;
    }
  }
}

Cypress.Commands.add("login", login);
Cypress.Commands.add("createSession", createSession);
Cypress.Commands.add("readSession", readSession);
Cypress.Commands.add("deleteSession", deleteSession);

function login() {
  cy.visit("/login");

  cy.intercept("POST", "/api/auth/login", {
    body: mockUser,
  });

  cy.intercept(
    {
      method: "GET",
      url: "/api/session",
    },
    [],
  ).as("session");

  cy.get("input[formControlName=email]").type("mddweb@studio.com");
  cy.get("input[formControlName=password]").type(`${"test!1234"}{enter}{enter}`);
}

function createSession() {
  cy.intercept(
    {
      method: "GET",
      url: "/api/teacher",
    },
    [mockTeacher],
  ).as("teachers");

  cy.intercept(
    {
      method: "post",
      url: "/api/session",
    },
    [],
  ).as("teachers");

  cy.intercept(
    {
      method: "get",
      url: "/api/session",
    },
    [mockSesstion],
  ).as("sessions");

  cy.get("button[routerlink=create]").click();
  cy.get("input[formControlName=name]").type(mockSesstion.name);
  cy.get("input[formControlName=date]").type(mockSesstion.date.toISOString().split("T")[0]);
  cy.get(".mat-mdc-form-field-type-mat-select > .mat-mdc-text-field-wrapper").click();
  cy.get("#mat-option-0").click();
  cy.get("#mat-input-4").type(mockSesstion.description);
  cy.get(
    "body > app-root > div > div > app-form > div > mat-card > mat-card-content > form > div > button",
  ).click();
}

function readSession() {
  cy.intercept(
    {
      method: "GET",
      url: "api/session/1",
    },
    mockSesstion,
  ).as("session1");

  cy.get(
    "body > app-root > div > div > app-list > div > mat-card > div > mat-card > mat-card-actions > button:nth-child(1)",
  ).click();
}

function deleteSession() {
  cy.intercept(
    {
      method: "DELETE",
      url: "api/session/1",
    },
    mockSesstion,
  ).as("sessiondel");

  cy.intercept(
    {
      method: "GET",
      url: "/api/session",
    },
    [],
  ).as("sessions");

  cy.get(
    "body > app-root > div > div > app-detail > div > mat-card > mat-card-title > div > div:nth-child(2) > button",
  ).click();
}
