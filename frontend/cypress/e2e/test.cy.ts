import {
  mockArticle,
  mockArticle2,
  mockComment,
  mockTopic,
  mockTopic2,
  mockUser,
} from "../mocks/mocks";

describe("spec", () => {
  it("successfull", () => {
    //
    // login
    //
    cy.visit("/sign");
    cy.url().should("include", "/sign");

    cy.get('[routerlink="/signup"] > .flex').click();
    cy.url().should("include", "/signup");

    cy.intercept("POST", "/auth/signup", {
      body: mockUser,
    });

    cy.intercept(
      {
        method: "GET",
        url: "/api/session",
      },
      [],
    ).as("session");

    cy.get('[name="Nom d\'utilisateur"]').type(mockUser.username);
    cy.get('[name="Addresse e-mail"]').type(mockUser.email);
    cy.get('[name="Mot de passe"]').type("12345678");

    cy.get(".h-full > app-button-component > .flex").click();

    cy.url().should("include", "/");

    //
    // logout
    //
    cy.get("app-button-component.text-red-500 > .flex").click();
    cy.url().should("include", "/");
    cy.url().should("include", "/sign");

    //
    // sign in
    //
    cy.intercept("POST", "/auth/signin", {
      body: mockUser,
    });
    cy.get('[routerlink="/signin"] > .flex').click();

    cy.get('[name="Nom d\'utilisateur ou E-mail"]').type(mockUser.username);
    cy.get('[name="Mot de passe"]').type("12345678");

    cy.get(".h-full > app-button-component > .flex").click();
    cy.url().should("include", "/");

    //
    // go to profile
    //
    cy.intercept("GET", "/auth/user", {
      body: mockUser,
    });

    cy.intercept("GET", "/api/topic/only-subscribed", {
      body: [mockTopic, mockTopic2],
    });

    cy.get(".cursor-pointer > .flex").click();

    cy.get('[placeholder="Username..."][style="display: contents;"] > .w-full').should(
      "have.value",
      mockUser.username,
    );

    cy.intercept("POST", "/auth/username", {
      body: mockUser,
    });
    cy.intercept("GET", "/auth/user", {
      body: { ...mockUser, username: "jane doe" },
    });

    cy.get('[placeholder="Username..."][style="display: contents;"] > .w-full')
      .clear()
      .type("jane doe");
    cy.get(".gap-8 > app-button-component.mx-auto > .flex").click();

    cy.get('[placeholder="Username..."][style="display: contents;"] > .w-full').should(
      "have.value",
      "jane doe",
    );

    cy.get(":nth-child(1) > .aspect-video").should("exist");
    cy.get(":nth-child(1) > .aspect-video > .text-left.font-semibold").should(
      "have.text",
      mockTopic.name,
    );
    cy.get(":nth-child(1) > .aspect-video > p.text-left").should(
      "have.text",
      mockTopic.description,
    );

    cy.get(":nth-child(2) > .aspect-video").should("exist");
    cy.get(":nth-child(2) > .aspect-video > .text-left.font-semibold").should(
      "have.text",
      mockTopic2.name,
    );
    cy.get(":nth-child(2) > .aspect-video > p.text-left").should(
      "have.text",
      mockTopic2.description,
    );

    //
    // Theme
    //
    const mockTopcis = [mockTopic, mockTopic2].map((e, i) => ({ ...e, amISubscribed: !!i }));

    cy.intercept("GET", "/api/topic/with-am-i-subscribed", {
      body: mockTopcis,
    });

    cy.get('[routerlink="/themes"] > .flex').click();

    mockTopcis[0].amISubscribed = !mockTopcis[0].amISubscribed;
    cy.intercept("POST", "/api/subscription/12345678", {});
    cy.intercept("GET", "/api/topic/with-am-i-subscribed", {
      body: mockTopcis,
    });
    cy.get(":nth-child(1) > .aspect-video > app-button-component.mx-auto > .flex").click();
    cy.get(":nth-child(1) > .aspect-video > app-button-component.mx-auto > .flex").should(
      "have.text",
      " Déjà abonné ",
    );

    mockTopcis[1].amISubscribed = !mockTopcis[1].amISubscribed;
    cy.intercept("GET", "/api/topic/with-am-i-subscribed", {
      body: mockTopcis,
    });
    cy.get(":nth-child(2) > .aspect-video > app-button-component.mx-auto > .flex").click();
    cy.get(":nth-child(2) > .aspect-video > app-button-component.mx-auto > .flex").should(
      "have.text",
      " S'abonner ",
    );

    //
    // GET Articles
    //
    const mockArtices = [mockArticle, mockArticle2];
    cy.intercept("GET", "/api/article", {
      body: mockArtices,
    });
    cy.get("app-button-component.text-violet-500 > .flex").click();
    cy.get(":nth-child(1) > .aspect-video > .font-semibold").should(
      "have.text",
      mockArtices[0].title,
    );

    cy.get(":nth-child(3) > app-button-component.mx-auto > .flex").click();

    cy.get(":nth-child(1) > .aspect-video > .font-semibold").should(
      "have.text",
      mockArtices[1].title,
    );

    //
    // Article detail
    //
    cy.intercept("GET", "/api/article/12345679", {
      body: mockArticle2,
    });
    cy.get(":nth-child(1) > .aspect-video > .font-semibold").click();

    cy.get(".h-full > :nth-child(1) > .gap-4 > .text-left").should("have.text", mockArticle2.title);
    cy.get(".h-full > :nth-child(1) > :nth-child(3)").should("have.text", mockArticle2.content);
    cy.get(".flex-wrap > :nth-child(2)").should("have.text", mockArticle2.author.username);
    cy.get(".flex-wrap > :nth-child(3)").should("have.text", mockArticle2.topic.name);

    cy.get("app-textarea-component > .w-full").type(mockComment.content);

    cy.intercept("POST", "/api/comment", {
      body: [],
    });

    cy.intercept("GET", "/api/comment/12345679", {
      body: [mockComment],
    });

    cy.get(
      "body > app-root > app-article-by-id-page > div > app-scroll-component > div > div > div > div.flex.h-40.items-center.gap-4 > button",
    ).click();

    cy.get(".items-end > .text-left").should("have.text", mockComment.author.username);
    cy.get(".items-end > .flex > p").should("have.text", mockComment.content);

    cy.get(".top-8").click();
    //
    // Create Articles
    //

    cy.intercept("POST", "/api/article", {});

    cy.get("app-button-component.text-violet-500 > .flex").click();
    cy.get(":nth-child(1) > app-button-component.mx-auto > .flex").click();
    cy.get("app-dropdown-component > .w-full").select(mockTopcis[0].name);
    cy.get("app-text-input-component > .w-full").type("New article name");
    cy.get("app-textarea-component.h-full > .w-full").type("New article desc");
    cy.get(".justify-between > app-button-component > .flex").click();

    cy.url().should("include", "/");
  });
});
