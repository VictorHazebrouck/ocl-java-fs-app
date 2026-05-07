describe("Login spec", () => {
  it("Login successfull", () => {
    cy.login();

    cy.url().should("include", "/sessions");
  });
});
