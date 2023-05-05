import { validCredentials, invalidCredentials } from "../fixtures/login";


describe("Valid Login Test", () => {
    it("should allow a user to log in with valid credentials", () => {
        cy.login(validCredentials);
        cy.location("pathname").should("include", "/app/dashboard");
    });
});
