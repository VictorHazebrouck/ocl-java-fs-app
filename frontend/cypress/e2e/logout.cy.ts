describe("Logout spec", () => {
  it("Logout successfull", () => {
    cy.login();
    cy.get("body > app-root > div > mat-toolbar > div > span:nth-child(3)").click();

    cy.url().should("include", "/login");
  });
});
